package com.dhhan.demo.controller;

import com.dhhan.customFramework.redis.RedisRepository;
import com.dhhan.demo.dto.MessageDTO;
import com.dhhan.demo.dto.response.CustomResponse;
import com.dhhan.demo.service.MessageService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    MessageService messageService;

    @Autowired
    RedisRepository redisRepository;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public CustomResponse sendMessage(@RequestBody MessageDTO messageDTO) throws Exception {

        if (StringUtils.isEmpty(messageDTO.getRoomId())) {
            String roomId = UUID.randomUUID().toString();

            // 1. redis rooms:userId 에 rooms 에 hset 으로 넣는다.
            Set<String> rooms = new HashSet<>();
            Optional<String> tmp = redisRepository.hget("rooms:" + messageDTO.getSenderId(), messageDTO.getRoomType().toString());
            tmp.ifPresent(roomStrinig -> rooms.addAll(Arrays.stream(roomStrinig.split(",")).toList()));
            rooms.add(roomId);
            redisRepository.hset("rooms:"+messageDTO.getSenderId(),messageDTO.getRoomType().toString(),rooms);
        }

        // 2. redis room:members:uuid 에 set 타입으로 사용자들을 넣는다.

        // 3. redis room:uuid 에 hash-set 로 메세지를 넣는다.

        // 3. redis room:order:uuid 에 list 로 uuid 집어넣는다.

        messageService.sendMessage(messageDTO);
        return CustomResponse.success(null);
    }

}
