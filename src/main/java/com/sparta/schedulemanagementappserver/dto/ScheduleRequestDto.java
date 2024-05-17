package com.sparta.schedulemanagementappserver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleRequestDto {
    private String title;
    private String content;
    private String assignee;
    private String password;
}
