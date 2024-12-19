package com.dhhan.demo.controller;

import com.dhhan.customFramework.utils.NetworkHelper;
import com.dhhan.demo.aop.StatusOkException;
import com.dhhan.demo.dto.LoginDTO;
import com.dhhan.demo.dto.response.CustomResponse;
import com.dhhan.demo.dto.type.CustomErrorCode;
import com.dhhan.demo.dto.type.CustomStatus;
import com.dhhan.demo.utils.JwtHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/room")
public class RoomController {

    @PostMapping("/users")
    public CustomResponse getUsers(HttpServletRequest request) throws Exception {


        return CustomResponse.success("사용자 목록 호출함 ㅋㅋㅋ");
    }
}
