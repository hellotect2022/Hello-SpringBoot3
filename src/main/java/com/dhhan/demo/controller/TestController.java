package com.dhhan.demo.controller;

import com.dhhan.customFramework.redis.PubSub;
import com.dhhan.customFramework.redis.RedisRepository;
import com.dhhan.customFramework.utils.LogHelper;
import com.dhhan.demo.dto.MemoryInfo;
import com.dhhan.demo.dto.response.CustomResponse;
import com.dhhan.demo.dto.type.CustomStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    PubSub pubSub;

    @Autowired
    RedisRepository redisRepository;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public CustomResponse helloWorld() {
        return new CustomResponse(CustomStatus.SUCCESS,"Hello world!!");
    }

    @RequestMapping(value = "/redis", method = RequestMethod.GET)
    public CustomResponse redis() {
        long result = pubSub.publish("test","messsssss");
        LogHelper.info("result = " + result,this);
        return new CustomResponse(CustomStatus.SUCCESS,"Hello world!!");
    }

    @RequestMapping(value = "/memory", method = RequestMethod.GET)
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

    @RequestMapping(value = "/set/{value}", method = RequestMethod.GET)
    public @ResponseBody CustomResponse redisSet(@PathVariable String value) throws Exception {
        redisRepository.setString("test",value);
        return CustomResponse.success("redis set: "+value);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public @ResponseBody CustomResponse redisGet() throws Exception {
        return CustomResponse.success("redis get: "+redisRepository.getString("test"));
    }


    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public @ResponseBody void error() throws Exception {
        throw new Exception();
    }

}
