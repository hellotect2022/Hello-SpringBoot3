package com.dhhan.demo.controller;

import com.dhhan.customFramework.utils.LogHelper;
import com.dhhan.demo.dto.MessageDTO;
import com.dhhan.demo.dto.response.CustomResponse;
import com.dhhan.demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    MessageService messageService;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public CustomResponse sendMessage(@RequestBody MessageDTO messageDTO) throws Exception {

        try {
            messageService.sendMessage(messageDTO);
        }catch (Exception e) {
            LogHelper.error(e);
            throw e;
        }

        return CustomResponse.success(null);
    }
}
