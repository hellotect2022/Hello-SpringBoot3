package com.dhhan.demo.dto.type;

public enum RoomType {
    Group("그룹채팅방"),
    Solo("개인채팅방"),
    Pair("1대1채팅방");

    private String description;
    RoomType(String s) {
        this.description = s;
    }
    public String getDescription() {
        return description;
    }
}
