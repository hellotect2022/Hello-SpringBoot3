package com.dhhan.demo.controller;

import com.dhhan.demo.dto.MemoryInfo;
import com.dhhan.demo.dto.response.CustomResponse;
import com.dhhan.demo.dto.type.CustomStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello, World!";
    }

    @GetMapping("/memory")
    public @ResponseBody CustomResponse showMemoryInfo() {
        return new CustomResponse(CustomStatus.SUCCESS,new MemoryInfo(Runtime.getRuntime()));
    }

}
