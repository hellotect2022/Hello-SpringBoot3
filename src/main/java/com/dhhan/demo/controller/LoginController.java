package com.dhhan.demo.controller;

import com.dhhan.customFramework.redis.RedisRepository;
import com.dhhan.customFramework.utils.JsonHelper;
import com.dhhan.customFramework.utils.NetworkHelper;
import com.dhhan.demo.aop.StatusOkException;
import com.dhhan.demo.dto.LoginDTO;
import com.dhhan.demo.dto.SampleDTO;
import com.dhhan.demo.dto.response.CustomResponse;
import com.dhhan.demo.dto.type.CustomErrorCode;
import com.dhhan.demo.dto.type.CustomStatus;
import com.dhhan.demo.utils.JwtHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.core.util.Json;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/na/nf")
public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RedisRepository redisRepository;

    @PostMapping("/login")
    public CustomResponse userLogin(@RequestBody LoginDTO requestParams, HttpServletRequest request) throws Exception {

//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginDTO.getName(), loginDTO.getEmail())
//        );
//
//        // 사용자 정보
//        String username = authentication.getName(); // 사용자 이름

        Optional<LoginDTO> userInfo = redisRepository.get("user:"+requestParams.getUserId(),LoginDTO.class);
        // 1. ID 검증
        userInfo.ifPresentOrElse(user -> {
            if (!user.getPasswd().equals(requestParams.getPasswd())) {
                throw new StatusOkException(CustomStatus.FAIL, CustomErrorCode.ERR_NO_MATCH_PASSWD);
            }
        },()->{
            throw new StatusOkException(CustomStatus.FAIL, CustomErrorCode.ERR_NO_USERS);
        });


        // 2 JWT 생성
        String token = JwtHelper.createToken(userInfo.get().getName(),userInfo.get().getUserId(),userInfo.get().getEmail());

        // 3 REDIS 에 사용자 토큰 그냥 저장함
        redisRepository.hset("token:"+requestParams.getUserId(), NetworkHelper.getClientType(request),token);
        return CustomResponse.success(token);
    }

    @PostMapping("/signUp")
    public CustomResponse userSginUp(@RequestBody LoginDTO requestParams) {

        // 1. ID 검증
        if (!redisRepository.get("user:"+requestParams.getUserId()).isEmpty()) {
            throw new StatusOkException(CustomStatus.FAIL, CustomErrorCode.ERR_DUP_USERS);
        }

        // 2 REDIS 에 사용자 토큰저장
        redisRepository.set("user:"+requestParams.getUserId(),requestParams);

        return CustomResponse.success("가입완료되었습니다.");
    }

}
