package com.dhhan.demo.config;


import com.dhhan.demo.filter.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity  //  Spring Security 컨텍스트 설정임을 명시한다.
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST = {
//            "/api/v1/member/**", "/swagger-ui/**","/swagger" ,"/api-docs",
//            "/v3/api-docs/**", "/api-docs/**", "/api/v1/auth/**", "/login","/api-docs/swagger-config","/test/**"
            "/*"
    };

    @Bean
    @Order(1)
    public SecurityFilterChain privateSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화 (API 사용시)
                .cors(Customizer.withDefaults())
                .formLogin(form->form.disable()) // form login disable
                .httpBasic(httpBasic->httpBasic.disable()) // basichttp 비뢀성화
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // session 관리 X
                .authorizeHttpRequests(authorize->authorize
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        //.anyRequest().authenticated()
                        .anyRequest().permitAll() // 그 외 요청은 인증 필요
                )
                .addFilterBefore(new JwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // AuthenticationManager를 Bean으로 등록하여 사용합니다.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
