package com.sparta.daengtionary.service;

import com.sparta.daengtionary.configration.error.CustomException;
import com.sparta.daengtionary.configration.error.ErrorCode;
import com.sparta.daengtionary.domain.*;
import com.sparta.daengtionary.dto.request.MapRequestDto;
import com.sparta.daengtionary.dto.response.MapImgResponseDto;
import com.sparta.daengtionary.dto.response.ResponseBodyDto;
import com.sparta.daengtionary.jwt.TokenProvider;
import com.sparta.daengtionary.repository.MapImgRepository;
import com.sparta.daengtionary.repository.MapInfoRepository;
import com.sparta.daengtionary.repository.MapRepository;
import com.sparta.daengtionary.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MapService {
    private final MapRepository mapRepository;
    private final MapInfoRepository mapInfoRepository;
    private final MapImgRepository mapImgRepository;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    private final FileUploadService fileUploadService;
    private final AwsS3UploadService s3UploadService;


    @Transactional
    public void createMap(MapRequestDto mapRequestDto, List<MultipartFile> mapImgs)
        throws IOException
    {
        Optional<Member> member = Optional.ofNullable(validateMember());
        //member null 검사
        Map map = Map.builder()
                .member(member.get())
                .title(mapRequestDto.getTitle())
                .content(mapRequestDto.getContent())
                .category(mapRequestDto.getCategory())
                .address(mapRequestDto.getAddress())
                .mapx(mapRequestDto.getMapx())
                .mapy(mapRequestDto.getMapy())
                .build();

        mapRepository.save(map);

        List<MapImg> imgList = new ArrayList<>();
        List<MapImgResponseDto> imgResponseDtoList = new ArrayList<>();
        List<MapInfo> mapInfos = new ArrayList<>();

        for(MultipartFile mapimg : mapImgs){
            MapImgResponseDto imgResponseDto = fileUploadService.uploadImage(mapimg,"map");
            imgResponseDtoList.add(imgResponseDto);
        }
        dtoParser(imgList,imgResponseDtoList,mapInfos,mapRequestDto);

        mapImgRepository.saveAll(imgList);
        mapInfoRepository.saveAll(mapInfos);
    }

    public Map loadMapByMapNo(Long mapNo){
        return mapRepository.findById(mapNo).orElseThrow(
                () -> new CustomException(ErrorCode.MAP_WRONG_INPUT)
        );
    }

    private void dtoParser(List<MapImg> imgList, List<MapImgResponseDto> imgResponseDtoList
        ,List<MapInfo> infoList , MapRequestDto requestDto){
        for(MapImgResponseDto imgResponseDto: imgResponseDtoList){
            MapImg mapImg = MapImg.builder()
                    .mapImgName(imgResponseDto.getImgName())
                    .mapImgUrl(imgResponseDto.getImgUrl())
                    .build();
            imgList.add(mapImg);
        }

        for(String info : requestDto.getMapInfos()){
            MapInfo mapInfo = MapInfo.builder()
                    .mapInfo(info)
                    .build();
            infoList.add(mapInfo);
        }
    }

    @Transactional
    public Member validateMember(){
        return tokenProvider.getMemberFromAuthentication();
    }

}