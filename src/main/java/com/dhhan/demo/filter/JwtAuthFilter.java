package com.dhhan.demo.filter;

import com.dhhan.demo.utils.JwtHelper;
import com.dhhan.demo.utils.LogHelper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

// Filter 는 tomcat servlet container 단에서 처리하는 로직임
// OncePerRequestFilter 각 요청에 대해 한 번만 실행
public class JwtAuthFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        LogHelper.info("Filter 에 들어왔음",this);

        // 1. 헤더에서 JWT 가져오기
        String token = JwtHelper.resolveToken(request);
        LogHelper.info("Filter token 체크 : "+ token,this);

        // 2. JWT 유효성 검사
        if (token != null && JwtHelper.validateToken(token)) {
            // 3. 토큰에서 사용자 정보 추출 및 인증 설정
            var authentication = JwtHelper.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 다음 필터로 진행
        filterChain.doFilter(request, response);
    }
}
