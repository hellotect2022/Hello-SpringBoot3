package com.dhhan.demo.controller;

import com.dhhan.customFramework.redis.RedisRepository;
import com.dhhan.customFramework.utils.NetworkHelper;
import com.dhhan.demo.aop.StatusOkException;
import com.dhhan.demo.dto.LoginDTO;
import com.dhhan.demo.dto.MessageDTO;
import com.dhhan.demo.dto.RoomDTO;
import com.dhhan.demo.dto.response.CustomResponse;
import com.dhhan.demo.dto.type.CustomErrorCode;
import com.dhhan.demo.dto.type.CustomStatus;
import com.dhhan.demo.utils.JwtHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    RedisRepository redisRepository;

    @PostMapping("/getbyuser")
    public CustomResponse getbyuser(@AuthenticationPrincipal LoginDTO loginDTO,  HttpServletRequest request) throws Exception {

        Set<Object> chatRoomIds = redisRepository.smembers("user:"+loginDTO.getUserId()+":chatroom");

        // 채팅방 정보 가져오기
        List<RoomDTO> roomSyncInfos = redisRepository.hmget("chatroom",chatRoomIds, RoomDTO.class);
        roomSyncInfos.forEach(roomDTO->{
            // 채팅방 참여자 가져오기
            Set<Object> members = redisRepository.smembers("chatroom:"+roomDTO.getRoomId()+":participants");
            roomDTO.setMembers(members.stream().map(Object::toString).collect(Collectors.toList()));

            // 채팅방 마지막 메세지 가져오기
            // LPOP 으로 채팅방 메세지 id 가져오기
            redisRepository.lrange("chatroom:"+roomDTO.getRoomId()+":messages",0,0,String.class)
                    .forEach(lstmsgId->{
                        roomDTO.setLastMessage(redisRepository.hget("message",lstmsgId, MessageDTO.class).get());
                    });
        });

        return CustomResponse.success(roomSyncInfos);
    }
}
