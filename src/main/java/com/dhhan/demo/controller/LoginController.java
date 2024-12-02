package com.dhhan.demo.controller;

import com.dhhan.demo.dto.LoginDTO;
import com.dhhan.demo.dto.SampleDTO;
import com.dhhan.demo.dto.response.CustomResponse;
import com.dhhan.demo.dto.type.CustomStatus;
import com.dhhan.demo.utils.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/login")
public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager;
    @PostMapping("/login")
    public CustomResponse helloWorld2(@RequestBody LoginDTO loginDTO) {

//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginDTO.getName(), loginDTO.getEmail())
//        );
//
//        // 사용자 정보
//        String username = authentication.getName(); // 사용자 이름

        String name = "John Doe";
        String nickname = "johnd";
        String email = "john.doe@example.com";

        // JWT 생성
        String token = JwtHelper.createToken(name,nickname,email);


        return new CustomResponse(CustomStatus.SUCCESS,token);
    }

}
