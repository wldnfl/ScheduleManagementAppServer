package com.sparta.schedulemanagementappserver.dto;

import lombok.Getter;

@Getter
public class CommentCreateRequest {
    private String content;
    private String username;

    public CommentCreateRequest(String content, String username) {
        this.content = content;
        this.username = username;
    }
}