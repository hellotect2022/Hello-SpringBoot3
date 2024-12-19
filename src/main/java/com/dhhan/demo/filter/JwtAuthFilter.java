package com.dhhan.demo.filter;

import com.dhhan.customFramework.redis.RedisRepository;
import com.dhhan.customFramework.utils.LogHelper;
import com.dhhan.customFramework.utils.NetworkHelper;
import com.dhhan.demo.aop.ForbiddenException;
import com.dhhan.demo.utils.JwtHelper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

// Filter 는 tomcat servlet container 단에서 처리하는 로직임
// OncePerRequestFilter 각 요청에 대해 한 번만 실행
public class JwtAuthFilter extends OncePerRequestFilter {

    private final RedisRepository redisRepository;

    // 생성자 주입
    public JwtAuthFilter(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        LogHelper.info(String.format("JWT Filter (%s)",servletPath),this);
        // 특정 경로는 필터 로직을 생략
        if (servletPath.indexOf("/na/nf") != -1 || servletPath.indexOf("/html") != -1) {
            filterChain.doFilter(request, response);
            return;
        }
        // 1. 헤더에서 JWT 가져오기
        String currentToken = JwtHelper.resolveToken(request);
        LogHelper.info("Filter token 체크 : "+ currentToken,this);

        // 2. JWT 유효성 검사 validateToken 에서유효날짜는 이미계산함
        if (currentToken != null && JwtHelper.validateToken(currentToken)) {
            // 1. 토큰을 해싱해서 결과 값을 가졍옴
            Claims claim = JwtHelper.hashingToken(currentToken);
            String userId = claim.get("userId").toString();

            // 1. 기존 redis 에 있는 값을 비교해서 1개의 토큰만 유효하도록 설정함
            Optional<String> redisToken = redisRepository.hget("token:"+userId, NetworkHelper.getClientType(request));

            if (redisToken.map(rToken -> rToken.equals(currentToken)).orElse(false)){
                var authentication = JwtHelper.getAuthentication(currentToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
                return;
            }
        }

        // 토큰이 유효하지 않거나 Redis에 존재하지 않는 경우 403 반환
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }
}
