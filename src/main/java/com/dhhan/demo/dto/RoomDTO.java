package com.dhhan.demo.dto;

import com.dhhan.demo.dto.type.RoomType;

import java.sql.Timestamp;
import java.util.List;

public class RoomDTO {
    private String roomId;        // roomId
    private RoomType roomType;    // 방 타입 Solo, Group, Pair
    private String title;         // 방 제목
    private Timestamp createdAt;  // 방생성 날짜
    private List<String> members; // 방 참여자 정보
    private MessageDTO lastMessage; // 방의 마지막 메세지

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public MessageDTO getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(MessageDTO lastMessage) {
        this.lastMessage = lastMessage;
    }
}
