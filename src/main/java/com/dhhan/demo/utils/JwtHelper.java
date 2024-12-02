package com.dhhan.demo.utils;

import com.dhhan.demo.dto.LoginDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Component
public class JwtHelper {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration_time}")
    private long accessTokenExpTime; // 만료 시간 (초 단위)

    private static Key key;

    private static long staticAccessTokenExpTime;
    @Autowired
    static UserDetailsService userDetailsService;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.staticAccessTokenExpTime = accessTokenExpTime;
    }

    public static String createToken(String username,String nickname,String email) {
        Instant now = Instant.now(); // 현재 시간
        Instant tokenValidity = now.plusSeconds(staticAccessTokenExpTime); // 만료 시간 계산

        // claim 은 JWT 의 payload 에 해당하는 부분
        Map<String, Object> claims = Map.of("sub", username,"nickname", nickname,"email", email);

        // JWT 생성
        return Jwts.builder()
                .setClaims(claims)                           // JWT 의 payload 에 포함될 데이터를 설정
                //.setSubject(username)                        // 사용자 정보 설정
                .setIssuedAt(Date.from(now))                 // 발행 시간
                .setExpiration(Date.from(tokenValidity))     // 만료 시간
                .signWith(key)                               // 서명
                .compact();
    }

    // 토큰에서 인증 정보 추출
    public static Authentication getAuthentication(String token) {
        // 1. JWT에서 사용자명 추출
        String username = getUsernameFromToken(token);
        LogHelper.info("Authenticated user ID: "+username,JwtHelper.class);

        // 2. 사용자명으로 UserDetails 조회 (사용자 권한 포함)
        //UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // 3. UsernamePasswordAuthenticationToken 생성 (사용자명, null, 권한 목록)
        // return new UsernamePasswordAuthenticationToken(username, null, AuthorityUtils.NO_AUTHORITIES);

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setName(username);
        // UsernamePasswordAuthenticationToken(Object principal == @AuthenticationPrincipal 에 들어갈 값)
        return new UsernamePasswordAuthenticationToken(loginDTO, null, AuthorityUtils.NO_AUTHORITIES);
    }

    // 토큰에서 사용자 이름 추출
    public static String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key) // 서명 키 설정
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // 토큰 유효성 검사
    public static boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(key) // 서명 키 설정
                    .build()
                    .parseClaimsJws(token); // 토큰 파싱 및 검증
            return true;
        } catch (Exception e) {
            return false; // 예외 발생 시 유효하지 않은 토큰으로 처리
        }
    }

    // 헤더에서 토큰 추출
    public static String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("dhhan_token");
        if (bearerToken != null) {
            return bearerToken;
        }
        return null;
//        String bearerToken = request.getHeader("Authorization");
//        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7); // "Bearer " 이후의 토큰 반환
//        }
//        return null;
    }
}
