package com.dhhan.demo.config;


import com.dhhan.demo.filter.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity  //  Spring Security 컨텍스트 설정임을 명시한다.
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/api/v1/member/**", "/swagger-ui/**","/swagger" ,"/api-docs",
            "/v3/api-docs/**", "/api-docs/**", "/api/v1/auth/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화 (API 사용시)
                .cors(Customizer.withDefaults())
                .formLogin(form->form.disable()) // form login disable
                .httpBasic(basic->basic.disable()) // basichttp 비뢀성화
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // session 관리 X
                .authorizeHttpRequests(authorize->authorize
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        //.anyRequest().permitAll()
                );

        //JwtAuthFilter를 UsernamePasswordAuthenticationFilter 앞에 추가
        //해당 filter 는 폼 기반 인증(form-based authentication)을 처리
        http.addFilterBefore(new JwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);



        return http.build();
    }
}
