package com.dhhan.demo.controller;

import com.dhhan.customFramework.utils.LogHelper;
import com.dhhan.demo.controller.openapi.SampleInterfaceOpenApi;
import com.dhhan.demo.dto.LoginDTO;
import com.dhhan.demo.dto.MemoryInfo;
import com.dhhan.demo.dto.SampleDTO;
import com.dhhan.demo.dto.response.CustomResponse;
import com.dhhan.demo.dto.type.CustomStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/sample")
public class SampleController implements SampleInterfaceOpenApi {
    @RequestMapping(value = "/hello/{number}", method = RequestMethod.GET)
    public CustomResponse helloWorld(@AuthenticationPrincipal LoginDTO loginDTO, @PathVariable String number) {
        return CustomResponse.success("Hello world!!"+loginDTO.getName());
    }

    @RequestMapping(value = "/hello2", method =RequestMethod.POST)
    public CustomResponse helloWorld2(@RequestBody SampleDTO sampleDTO) {
        return CustomResponse.success(sampleDTO);
    }

    @RequestMapping(value = "/memory", method = RequestMethod.GET)
    public @ResponseBody CustomResponse showMemoryInfo() {

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
        return CustomResponse.success(test0);

    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public void error() {
        throw new RuntimeException();
    }

}
