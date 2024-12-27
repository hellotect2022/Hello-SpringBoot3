package com.dhhan.demo.controller;

import com.dhhan.customFramework.redis.RedisRepository;
import com.dhhan.customFramework.utils.JsonHelper;
import com.dhhan.demo.dto.MessageDTO;
import com.dhhan.demo.dto.RoomDTO;
import com.dhhan.demo.dto.response.CustomResponse;
import com.dhhan.demo.service.MessageService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
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
            RoomDTO roomDTO = new RoomDTO();
            roomDTO.setRoomId(roomId);
            roomDTO.setRoomType(messageDTO.getRoomType());
            roomDTO.setTitle("1:1 Chat");
            roomDTO.setCreatedAt(new Timestamp(new Date().getTime()));

            ///// ////////////// //////////////////////////////

            // 1. REDIS HSET chatroom, {chatroomId} {~} 으로 채팅방 개설 함
            redisRepository.hset("chatroom",roomId,roomDTO);
            messageDTO.setRoomId(roomId);
        }

        messageDTO.setMessageId(UUID.randomUUID().toString());

        // 2. SADD user:{userId}:chatrooms {chatroomId1} {chatroomId2} (사용자별 참여된 채팅방 목록 추가)
        redisRepository.sadd("user:"+messageDTO.getSenderId()+":chatroom", messageDTO.getRoomId());
        redisRepository.sadd("user:"+messageDTO.getTargetId()+":chatroom", messageDTO.getRoomId());

        // 3. 채팅방별 참여자 목록 추가
        Set<String> members = new HashSet<String>();
        members.addAll(Arrays.asList(messageDTO.getSenderId(), messageDTO.getTargetId()));
        redisRepository.sadd("chatroom:"+messageDTO.getRoomId()+":participants",members.toArray());

        // 4. RPUSH chatroom:{chatroomId}:messages {messageId} (방별로 메세지 ID 만 저장하기)
        redisRepository.lpush("chatroom:"+messageDTO.getRoomId()+":messages", messageDTO.getMessageId());

        // 5. HSET message messageId  메세지의 구체적인 내뇽을 저장
        redisRepository.hset("message",messageDTO.getMessageId(), messageDTO);

        messageService.sendMessage(messageDTO);

        // 사용자 사용시간 갱신

        return CustomResponse.success(messageDTO);
    }

}
