package com.sparta.schedulemanagementappserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentRequestDto {
    private String username;
    private String content;
    private String schedule_id;
}
