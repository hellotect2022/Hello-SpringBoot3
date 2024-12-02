package com.dhhan.demo.controller;

import com.dhhan.customFramework.redis.PubSub;
import com.dhhan.customFramework.utils.LogHelper;
import com.dhhan.demo.dto.MemoryInfo;
import com.dhhan.demo.dto.response.CustomResponse;
import com.dhhan.demo.dto.type.CustomStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    PubSub pubSub;

    @GetMapping("/hello")
    public CustomResponse helloWorld() {
        return new CustomResponse(CustomStatus.SUCCESS,"Hello world!!");
    }

    @GetMapping("/redis")
    public CustomResponse redis() {
        long result = pubSub.publish("test","messsssss");
        LogHelper.info("result = " + result,this);
        return new CustomResponse(CustomStatus.SUCCESS,"Hello world!!");
    }

    @GetMapping("/memory")
    public @ResponseBody CustomResponse showMemoryInfo() throws IllegalAccessException {
        MemoryInfo test0 = new MemoryInfo(Runtime.getRuntime());
        HashMap<String, Object> test00 = new HashMap<String, Object>();
        test00.put("a", new MemoryInfo(Runtime.getRuntime()));
        test00.put("1", "aaa");

        ArrayList<MemoryInfo> test1 = new ArrayList<MemoryInfo>();
        test1.add(new MemoryInfo(Runtime.getRuntime()));
        test1.add(new MemoryInfo(Runtime.getRuntime()));
        test1.add(new MemoryInfo(Runtime.getRuntime()));

        ArrayList<HashMap> test2 = new ArrayList<HashMap>();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("a", new MemoryInfo(Runtime.getRuntime()));
        map.put("1", "aaa");
        HashMap<String, Object> map2 = new HashMap<String, Object>();
        map2.put("a", new MemoryInfo(Runtime.getRuntime()));
        map2.put("1", "aaa");
        test2.add(map);
        test2.add(map2);

        LogHelper.info(test0,this);
        LogHelper.info(test00,this);
        LogHelper.info(test1,this);
        LogHelper.info(test2,this);
        return new CustomResponse(CustomStatus.SUCCESS,test0);

    }

    @GetMapping("/error")
    public @ResponseBody void error() throws Exception {
        throw new Exception();
    }

}
