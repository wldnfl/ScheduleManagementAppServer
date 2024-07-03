package com.sparta.schedulemanagementappserver.service;

import com.sparta.schedulemanagementappserver.dto.ScheduleRequestDto;
import com.sparta.schedulemanagementappserver.dto.ScheduleResponse;
import com.sparta.schedulemanagementappserver.entity.Schedule;
import com.sparta.schedulemanagementappserver.exception.PasswordMismatchException;
import com.sparta.schedulemanagementappserver.exception.ScheduleAlreadyDeletedException;
import com.sparta.schedulemanagementappserver.exception.ScheduleNotFoundException;
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
        schedule.setDeleted(false);
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
                .orElseThrow(() -> new ScheduleNotFoundException("일정을 찾을 수 없습니다."));
        return ScheduleResponse.toDto(schedule);
    }

    @Transactional
    public ScheduleResponse updateSchedule(Long id, ScheduleRequestDto requestDto) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException("일정을 찾을 수 없습니다."));

        if (!schedule.getPassword().equals(requestDto.getPassword())) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }
        schedule.update(requestDto);
        return ScheduleResponse.toDto(schedule);
    }

    @Transactional
    public void deleteSchedule(Long id, String password) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException("일정을 찾을 수 없습니다. ID: " + id));

        if (!schedule.getPassword().equals(password)) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }

        if (schedule.isDeleted()) {
            throw new ScheduleAlreadyDeletedException("이미 삭제된 일정입니다. ID: " + id);
        }

        schedule.setDeleted(true);
        scheduleRepository.save(schedule);
    }

//    public ScheduleResponse findById(Long id) {
//        return ScheduleResponse.toDto(findScheduleById(id));
//    }

    public Schedule findScheduleById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException("해당 ID에 맞는 일정을 찾을 수 없습니다."));
    }
}
