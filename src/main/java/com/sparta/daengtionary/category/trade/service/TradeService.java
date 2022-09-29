package com.sparta.daengtionary.category.trade.service;

import com.sparta.daengtionary.aop.amazon.AwsS3UploadService;
import com.sparta.daengtionary.aop.dto.ResponseBodyDto;
import com.sparta.daengtionary.aop.exception.CustomException;
import com.sparta.daengtionary.aop.exception.ErrorCode;
import com.sparta.daengtionary.aop.jwt.TokenProvider;
import com.sparta.daengtionary.aop.supportrepository.PostRepositorySupport;
import com.sparta.daengtionary.category.member.domain.Member;
import com.sparta.daengtionary.category.trade.domain.Trade;
import com.sparta.daengtionary.category.trade.domain.TradeImg;
import com.sparta.daengtionary.category.trade.dto.request.TradeRequestDto;
import com.sparta.daengtionary.category.trade.dto.response.TradeDetailResponseDto;
import com.sparta.daengtionary.category.trade.dto.response.TradeResponseDto;
import com.sparta.daengtionary.category.trade.repository.TradeImgRepository;
import com.sparta.daengtionary.category.trade.repository.TradeRepository;
import com.sparta.daengtionary.category.wish.domain.Wish;
import com.sparta.daengtionary.category.wish.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TradeService {
    private final TradeRepository tradeRepository;
    private final TradeImgRepository tradeImgRepository;
    private final AwsS3UploadService s3UploadService;
    private final ResponseBodyDto responseBodyDto;
    private final TokenProvider tokenProvider;
    private final PostRepositorySupport postRepositorySupport;
    private final WishRepository wishRepository;
    private final String imgPath = "/map/image";

    @Transactional
    public ResponseEntity<?> createTrade(TradeRequestDto requestDto, List<MultipartFile> multipartFiles) {
        Member member = tokenProvider.getMemberFromAuthentication();



        Trade trade = Trade.builder()
                .member(member)
                .title(requestDto.getTitle())
                .address(requestDto.getAddress())
                .stuffStatus(requestDto.getStuffStatus())
                .exchange(requestDto.getExchange())
                .content(requestDto.getContent())
                .price(requestDto.getPrice())
                .postStatus(requestDto.getPostStatus())
                .build();

        tradeRepository.save(trade);

        if(multipartFiles.get(0).getSize() > 0){
            List<String> tradeImgList = s3UploadService.uploadListImg(multipartFiles, imgPath);
            List<TradeImg> tradeImgs = new ArrayList<>();
            for (String img : tradeImgList) {
                tradeImgs.add(
                        TradeImg.builder()
                                .trade(trade)
                                .tradeImg(img)
                                .build()
                );
            }

            tradeImgRepository.saveAll(tradeImgs);
        }


        return responseBodyDto.success("생성 완료");
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getTradeSort(String sort, int pagenum, int pagesize) {
        String title, content, nick, address, postStatus;
        title = "";
        content = "";
        nick = "";
        address = "";
        postStatus = "";
        List<TradeResponseDto> responseDtoList = postRepositorySupport.findAllByTrade(title, content, nick, address, postStatus, sort, pagenum, pagesize);

        return responseBodyDto.success(responseDtoList, "조회 성공");
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getSearchTrade(String title, String content, String nick, String address, String postStatus,
                                            String sort, int pagenum, int pagesize) {
        List<TradeResponseDto> responseDtoList = postRepositorySupport.findAllByTrade(title, content, nick, address, postStatus, sort, pagenum, pagesize);
        return responseBodyDto.success(responseDtoList, "조회 성공");
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getTrade(Long tradeNo) {
        Trade trade = validateTrade(tradeNo);

        List<TradeImg> tradeImgs = tradeImgRepository.findAllByTrade(trade);
        List<String> traImgs = new ArrayList<>();
        for (TradeImg i : tradeImgs) {
            traImgs.add(i.getTradeImg());
        }

        List<Wish> temp = wishRepository.findAllByTrade(trade);

        return responseBodyDto.success(
                TradeDetailResponseDto.builder()
                        .tradeNo(trade.getTradeNo())
                        .nick(trade.getMember().getNick())
                        .title(trade.getTitle())
                        .content(trade.getContent())
                        .price(trade.getPrice())
                        .view(trade.getView())
                        .address(trade.getAddress())
                        .exchange(trade.getExchange())
                        .stuffStatus(trade.getStuffStatus())
                        .postStatus(trade.getPostStatus())
                        .tradeImgUrl(traImgs)
                        .wishCount((long) temp.size())
                        .createdAt(trade.getCreatedAt())
                        .modifiedAt(trade.getModifiedAt())
                        .build(), "조회 성공"
        );
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getMyPostByTrade() {
        Member member = tokenProvider.getMemberFromAuthentication();

        List<Trade> tradeList = tradeRepository.findByMember(member);
        List<TradeResponseDto> tradeResponseDtoList = new ArrayList<>();

        for (Trade trade : tradeList) {
            tradeResponseDtoList.add(
                    TradeResponseDto.builder()
                            .tradeNo(trade.getTradeNo())
                            .nick(member.getNick())
                            .title(trade.getTitle())
                            .view(trade.getView())
                            .tradeImg(trade.getTradeImgs().get(0).getTradeImg())
                            .createdAt(trade.getCreatedAt())
                            .modifiedAt(trade.getModifiedAt())
                            .build()
            );
        }

        return responseBodyDto.success(tradeResponseDtoList, "조회 성공");
    }

    @Transactional
    public ResponseEntity<?> tradeUpdate(TradeRequestDto requestDto, Long tradeNo, List<MultipartFile> multipartFiles) {
        Member member = tokenProvider.getMemberFromAuthentication();
        Trade trade = validateTrade(tradeNo);
        trade.validateMember(member);

        List<TradeImg> deleteImg = tradeImgRepository.findAllByTrade(trade);
        for (TradeImg i : deleteImg) {
            s3UploadService.deleteFile(i.getTradeImg());
        }
        tradeImgRepository.deleteAll(deleteImg);

        if(multipartFiles.get(0).getSize() > 0){
            List<String> tradeImgs = s3UploadService.uploadListImg(multipartFiles, imgPath);

            List<TradeImg> saveImg = new ArrayList<>();
            for (String i : tradeImgs) {
                saveImg.add(
                        TradeImg.builder()
                                .trade(trade)
                                .tradeImg(i)
                                .build()
                );
            }

            tradeImgRepository.saveAll(saveImg);
        }

        trade.updateTrade(requestDto);

        return responseBodyDto.success("수정 성공"
        );
    }

    @Transactional
    public ResponseEntity<?> tradeDelete(Long tradeNo) {
        Member member = tokenProvider.getMemberFromAuthentication();
        Trade trade = validateTrade(tradeNo);
        trade.validateMember(member);
        List<TradeImg> deleteImg = tradeImgRepository.findAllByTrade(trade);
        for (TradeImg i : deleteImg) {
            s3UploadService.deleteFile(i.getTradeImg());
        }
        tradeImgRepository.deleteAll(deleteImg);
        tradeRepository.delete(trade);

        return responseBodyDto.success("삭제 성공");
    }


    @Transactional
    public void tradeViewUpdate(Long tradeNo) {
        Trade trade = validateTrade(tradeNo);
        trade.viewUpdate();
    }

    @Transactional(readOnly = true)
    public Trade validateTrade(Long TradeNo) {
        return tradeRepository.findById(TradeNo).orElseThrow(
                () -> new CustomException(ErrorCode.MAP_NOT_FOUND)
        );
    }

}