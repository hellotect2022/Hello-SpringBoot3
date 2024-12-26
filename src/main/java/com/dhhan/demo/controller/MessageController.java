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
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
            ///// 채팅방에 대한 정보(나중에 객체로 변환) //////////////
            Map<String,Object> roomObject = new HashMap<>();
            roomObject.put("roomId", roomId);
            roomObject.put("roomType", messageDTO.getRoomType().toString());
            roomObject.put("title", "1:1 Chat");
            roomObject.put("createdAt", new Date());
            ///// ////////////// //////////////////////////////
            // 1. REDIS HSET chatroom:{chatroomId} 으로 채팅방 개설 함
            redisRepository.hsetAll("chatroom:"+roomId,roomObject);
            messageDTO.setRoomId(roomId);
        }

        // 2. SADD user:{userId}:chatrooms {chatroomId1} {chatroomId2} (사용자별 참여된 채팅방 목록 추가)
        redisRepository.sadd("user:"+messageDTO.getSenderId()+":chatroom", messageDTO.getRoomId());
        redisRepository.sadd("user:"+messageDTO.getTargetId()+":chatroom", messageDTO.getRoomId());

        // 3. 채팅방별 참여자 목록 추가
        Set<String> members = new HashSet<String>();
        members.addAll(Arrays.asList(messageDTO.getSenderId(), messageDTO.getTargetId()));
        redisRepository.sadd("chatroom:"+messageDTO.getRoomId()+":participants",members.toArray());

        ///????


        messageService.sendMessage(messageDTO);
        return CustomResponse.success(null);
    }

}
