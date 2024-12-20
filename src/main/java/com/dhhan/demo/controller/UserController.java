package com.dhhan.demo.controller;

import com.dhhan.customFramework.redis.RedisRepository;
import com.dhhan.demo.dto.UserDTO;
import com.dhhan.demo.dto.response.CustomResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    RedisRepository redisRepository;
    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    public CustomResponse getUsers(HttpServletRequest request) throws Exception {

        Set<String> userkeys = redisRepository.getKeysByPattern("user:*");
        List<UserDTO> users = redisRepository.mget(userkeys.stream().toList(), UserDTO.class);
        return CustomResponse.success(users);
    }
}
