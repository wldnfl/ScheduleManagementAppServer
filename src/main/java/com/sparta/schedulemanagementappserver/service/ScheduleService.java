package com.sparta.schedulemanagementappserver.service;

import com.sparta.schedulemanagementappserver.dto.ScheduleRequestDto;
import com.sparta.schedulemanagementappserver.dto.ScheduleResponse;
import com.sparta.schedulemanagementappserver.entity.Schedule;
import com.sparta.schedulemanagementappserver.exception.DataNotFoundException;
import com.sparta.schedulemanagementappserver.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleResponse createSchedule(ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = new Schedule();
        schedule.setTitle(scheduleRequestDto.getTitle());
        schedule.setContent(scheduleRequestDto.getContent());
        schedule.setPassword(scheduleRequestDto.getPassword());
        schedule.setUsername(scheduleRequestDto.getUsername());
        schedule.setCreatedAt(LocalDateTime.now());
        scheduleRepository.save(schedule);
        return ScheduleResponse.toDto(schedule);
    }

    public List<ScheduleResponse> getAllSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules.stream()
                .map(ScheduleResponse::toDto)
                .collect(Collectors.toList());
    }

    public ScheduleResponse getSelectedSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일정을 찾을 수 없습니다."));
        return ScheduleResponse.toDto(schedule);
    }

    @Transactional
    public ScheduleResponse updateSchedule(Long id, ScheduleRequestDto requestDto) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일정을 찾을 수 없습니다."));
        schedule.update(requestDto);
        return ScheduleResponse.toDto(schedule);
    }

    public void deleteSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일정을 찾을 수 없습니다."));
        scheduleRepository.delete(schedule);
    }

    public ScheduleResponse findById(Long id) {
        return ScheduleResponse.toDto(findScheduleById(id));
    }

    public Schedule findScheduleById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("해당 ID에 맞는 일정을 찾을 수 없습니다."));
    }
}
