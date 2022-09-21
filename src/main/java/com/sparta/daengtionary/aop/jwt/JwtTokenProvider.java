package com.sparta.daengtionary.aop.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    // secretKey 와 같은 민감정보는 숨기는 것이 좋다.
    //정석대로 하려면 byte수에 맞게 디코딩해서 시크릿키를 구성해야하는데

    @Value("${jwt.secret}")
    private String secretKey;

    // 토큰 유효시간 5분 설정 (1000L = 1초, 1000L * 60 = 1분)
    private static final long TOKEN_VALID_TIME = 1000L * 60 * 1440;

    private final UserDetailsService userDetailsService;

    // 객체 초기화, secretKey 를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT 토큰 생성
    public String createToken(String userPk , String nickname , Long memberid) {
        Claims claims = Jwts.claims().setSubject(userPk); // JWT payload 에 저장되는 정보단위

        Map<String , Object> claimsMap = new HashMap<String , Object>();
        claimsMap.put("username" ,userPk);
        claimsMap.put("nickname" , nickname);
        claimsMap.put("memberid" , memberid);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claimsMap)
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + TOKEN_VALID_TIME)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과 signature 에 들어갈 secret값 세팅
                .compact();
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("username", String.class);
    }
    public String getNickName(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("nickname", String.class);
    }
    public Long getMemberid(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("memberid", Long.class);
    }

    // Request의 Header에서 token 값을 가져옵니다. "X-AUTH-TOKEN" : "TOKEN값'
    public static String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }


    public final String HEADER_PREFIX = "Bearer ";


    public  String extract(String header) {
        if (header == null || header.equals("") || header.length() < HEADER_PREFIX.length()) {
            throw new NoSuchElementException("올바른 JWT 정보가 아닙니다.");
        }

        return header.substring(
                HEADER_PREFIX.length(),
                header.length()
        );
    }

}