package com.sparta.schedulemanagementappserver.dto;

import com.sparta.schedulemanagementappserver.model.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private String title;
    private String content;
    private String assignee;
    private LocalDateTime createdAt;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.assignee = schedule.getAssignee();
        this.createdAt = schedule.getCreatedAt();
    }
}
