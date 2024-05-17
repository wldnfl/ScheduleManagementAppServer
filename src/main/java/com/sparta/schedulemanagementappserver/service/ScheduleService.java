package com.sparta.schedulemanagementappserver.service;

import com.sparta.schedulemanagementappserver.dto.ScheduleRequestDto;
import com.sparta.schedulemanagementappserver.dto.ScheduleResponseDto;
import com.sparta.schedulemanagementappserver.model.Schedule;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {
    private final List<Schedule> schedules = new ArrayList<>();
    private long nextId = 1;

    public ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = new Schedule();
        schedule.setId(nextId++);
        schedule.setTitle(scheduleRequestDto.getTitle());
        schedule.setContent(scheduleRequestDto.getContent());
        schedule.setAssignee(scheduleRequestDto.getAssignee());
        schedule.setPassword(scheduleRequestDto.getPassword());
        schedule.setCreatedAt(LocalDateTime.now());
        schedules.add(schedule);
        return new ScheduleResponseDto(schedule);
    }

    public List<ScheduleResponseDto> getAllSchedules() {
        List<ScheduleResponseDto> responseDtoList = new ArrayList<>();
        for (Schedule schedule : schedules) {
            responseDtoList.add(new ScheduleResponseDto(schedule));
        }
        return responseDtoList;
    }

    public ScheduleResponseDto getSelectedSchedule(Long id) {
        for (Schedule schedule : schedules) {
            if (schedule.getId().equals(id)) {
                return new ScheduleResponseDto(schedule);
            }
        }
        return null; // or throw exception if not found
    }
}
