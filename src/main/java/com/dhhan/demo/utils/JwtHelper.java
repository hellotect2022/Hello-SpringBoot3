package com.dhhan.demo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

public class JwtHelper {

    private final Key key;
    private final long accessTokenExpTime; // 만료 시간 (초 단위)
    private final UserDetailsService userDetailsService;

    public JwtHelper(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.expiration_time}") long accessTokenExpTime,
            UserDetailsService userDetailsService
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpTime = accessTokenExpTime;
        this.userDetailsService = userDetailsService;
    }

    // 토큰 생성
    public String createToken(String username, long expireTime) {
        Instant now = Instant.now(); // 현재 시간
        Instant tokenValidity = now.plusSeconds(expireTime); // 만료 시간 계산

        Map<String, Object> claims = Map.of("sub", username);

        // JWT 생성
        return Jwts.builder()
                .setClaims(claims)                           // 클레임 설정
                .setSubject(username)                        // 사용자 정보 설정
                .setIssuedAt(Date.from(now))                 // 발행 시간
                .setExpiration(Date.from(tokenValidity))     // 만료 시간
                .signWith(key)                               // 서명
                .compact();
    }

    // 토큰에서 인증 정보 추출
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 사용자 이름 추출
    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key) // 서명 키 설정
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
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
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 이후의 토큰 반환
        }
        return null;
    }
}
