package com.sparta.daengtionary.service;

import com.sparta.daengtionary.configration.error.CustomException;
import com.sparta.daengtionary.configration.error.ErrorCode;
import com.sparta.daengtionary.domain.*;
import com.sparta.daengtionary.dto.request.TradeRequestDto;
import com.sparta.daengtionary.dto.response.ResponseBodyDto;
import com.sparta.daengtionary.dto.response.TradeDetailResponseDto;
import com.sparta.daengtionary.dto.response.TradeResponseDto;
import com.sparta.daengtionary.jwt.TokenProvider;
import com.sparta.daengtionary.repository.MemberRepository;
import com.sparta.daengtionary.repository.TradeImgRepository;
import com.sparta.daengtionary.repository.TradeRepository;
import com.sparta.daengtionary.repository.supportRepository.MapRepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    private final MemberRepository memberRepository;
    private final ResponseBodyDto responseBodyDto;
    private final TokenProvider tokenProvider;

    private final MapRepositorySupport mapRepositorySupport;

    @Transactional
    public ResponseEntity<?> createTrade(TradeRequestDto requestDto, List<MultipartFile> multipartFiles){
        Member member = tokenProvider.getMemberFromAuthentication();
        validateFile(multipartFiles);
        List<String> tradeImgList = s3UploadService.upload(multipartFiles);

        Trade trade = Trade.builder()
                .member(member)
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .price(requestDto.getPrice())
                .build();

        tradeRepository.save(trade);

        List<TradeImg> tradeImgs = new ArrayList<>();
        for(String img : tradeImgList){
            tradeImgs.add(
                    TradeImg.builder()
                            .trade(trade)
                            .tradeImg(img)
                            .build()
            );
        }

        tradeImgRepository.saveAll(tradeImgs);

        return responseBodyDto.success(
                TradeDetailResponseDto.builder()
                        .tradeNo(trade.getTradeNo())
                        .nick(member.getNick())
                        .title(trade.getTitle())
                        .content(trade.getContent())
                        .price(trade.getPrice())
                        .status(trade.getStatus())
                        .view(trade.getView())
                        .tradeImgUrl(tradeImgList)
                        .createdAt(trade.getCreatedAt())
                        .modifiedAt(trade.getModifiedAt())
                        .build(),"생성완료"
        );
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getTradeSort(String sort, Pageable pageable){
        PageImpl<TradeResponseDto> responseDtos = mapRepositorySupport.findAllByTrade(pageable);

        return responseBodyDto.success(responseDtos,"조회 완료");
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getTrade(Long tradeNo){
        Trade trade = validateTrade(tradeNo);

        List<TradeImg> tradeImgs = tradeImgRepository.findAllByTrade(trade);
        List<String> traImgs = new ArrayList<>();
        for(TradeImg i : tradeImgs){
            traImgs.add(i.getTradeImg());
        }

        return responseBodyDto.success(
                  TradeDetailResponseDto.builder()
                          .tradeNo(trade.getTradeNo())
                          .nick(trade.getMember().getNick())
                          .title(trade.getTitle())
                          .content(trade.getContent())
                          .price(trade.getPrice())
                          .view(trade.getView())
                          .status(trade.getStatus())
                          .tradeImgUrl(traImgs)
                          .createdAt(trade.getCreatedAt())
                          .modifiedAt(trade.getModifiedAt())
                          .build(),"조회 성공"
        );
    }

    @Transactional
    public ResponseEntity<?> tradeUpdate(TradeRequestDto requestDto, Long tradeNo,List<MultipartFile> multipartFiles){
        Member member = tokenProvider.getMemberFromAuthentication();
        Trade trade = validateTrade(tradeNo);
        trade.validateMember(member);
        validateFile(multipartFiles);

        List<TradeImg> deleteImg = tradeImgRepository.findAllByTrade(trade);
        for(TradeImg i : deleteImg){
            s3UploadService.deleteFile(i.getTradeImg());
        }
        tradeImgRepository.deleteAll(deleteImg);

        List<String> tradeImgs = s3UploadService.upload(multipartFiles);

        List<TradeImg> saveImg = new ArrayList<>();
        for(String i : tradeImgs){
            saveImg.add(
                    TradeImg.builder()
                            .trade(trade)
                            .tradeImg(i)
                            .build()
            );
        }

        tradeImgRepository.saveAll(saveImg);
        trade.updateTrade(requestDto);

        return responseBodyDto.success(
                      TradeDetailResponseDto.builder()
                              .tradeNo(trade.getTradeNo())
                              .nick(member.getNick())
                              .title(trade.getTitle())
                              .content(trade.getContent())
                              .status(trade.getStatus())
                              .tradeImgUrl(tradeImgs)
                              .createdAt(trade.getCreatedAt())
                              .modifiedAt(trade.getModifiedAt())
                              .build(),"수정 성공"
        );


    }

    @Transactional
    public ResponseEntity<?> tradeDelete(Long tradeNo){
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
    public void tradeViewUpdate(Long tradeNo){
        Trade trade = validateTrade(tradeNo);
        trade.viewUpdate();
    }




    @Transactional(readOnly = true)
    public Trade validateTrade(Long TradeNo) {
        return tradeRepository.findById(TradeNo).orElseThrow(
                () -> new CustomException(ErrorCode.MAP_NOT_FOUND)
        );
    }

    private void validateFile(List<MultipartFile> multipartFiles) {
        if (multipartFiles == null) {
            throw new CustomException(ErrorCode.WRONG_INPUT_CONTENT);
        }
    }

    private Member validateMember(Long memberNo) {
        return memberRepository.findById(memberNo).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_USER_INFO)
        );
    }


}
