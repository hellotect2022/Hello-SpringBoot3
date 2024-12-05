package com.dhhan.demo.service;

import com.dhhan.customFramework.redis.PubSub;
import com.dhhan.customFramework.utils.JsonHelper;
import com.dhhan.demo.dto.MessageDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    PubSub pubSub;

    public void sendMessage(MessageDTO message) {
        pubSub.publish("channel-test", JsonHelper.parseObjectToJson(message));
    }
}
