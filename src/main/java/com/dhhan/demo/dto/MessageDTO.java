package com.dhhan.demo.dto;

import com.dhhan.demo.dto.type.RoomType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class MessageDTO {
    private String messageId;
    private String senderId;
    private String targetId;
    private String message;
    private String roomId;
    protected RoomType roomType;
    private Timestamp sendTime;
    private int unreadCnt;
}
