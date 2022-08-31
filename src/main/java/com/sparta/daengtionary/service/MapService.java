package com.sparta.daengtionary.service;

import com.sparta.daengtionary.domain.Member;
import com.sparta.daengtionary.dto.request.MapRequestDto;
import com.sparta.daengtionary.dto.response.ResponseBodyDto;
import com.sparta.daengtionary.jwt.TokenProvider;
import com.sparta.daengtionary.repository.MapImgRepository;
import com.sparta.daengtionary.repository.MapInfoRepository;
import com.sparta.daengtionary.repository.MapRepository;
import com.sparta.daengtionary.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class MapService {
    private final MapRepository mapRepository;
    private final MapInfoRepository mapInfoRepository;
    private final MapImgRepository mapImgRepository;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;


//    public ResponseBodyDto createMap(MapRequestDto mapRequestDto, HttpServletRequest httpServletRequest){
//        if(null == httpServletRequest.getHeader("Authorization")){
//            return ResponseBodyDto.fail("MEMBER_NOT_FOUND",
//                    "로그인이 필요합니다.");
//        }
//        Member member = validateMember();
//        if (null == member) {
//            return ResponseBodyDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
//        }
//    }

//    public Member validateMember(){
//        return tokenProvider.getMemberFromAuthentication();
//    }
}