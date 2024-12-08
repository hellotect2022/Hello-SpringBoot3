package com.dhhan.demo.dto.chatting;

import com.dhhan.demo.dto.type.RoomType;

import java.sql.Timestamp;

public class RoomDTO {
    private String roomId;
    private RoomType roomType;
    private Timestamp createdTime;
    private String[] usersIdList;
}
