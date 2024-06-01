package com.sparta.schedulemanagementappserver.service;

import com.sparta.schedulemanagementappserver.dto.CommentCreateRequest;
import com.sparta.schedulemanagementappserver.dto.CommentResponse;
import com.sparta.schedulemanagementappserver.dto.CommentUpdateRequest;
import com.sparta.schedulemanagementappserver.entity.Comment;
import com.sparta.schedulemanagementappserver.entity.Schedule;
import com.sparta.schedulemanagementappserver.exception.DataNotFoundException;
import com.sparta.schedulemanagementappserver.repository.CommentRepository;
import com.sparta.schedulemanagementappserver.repository.ScheduleRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleService scheduleService;

    @Transactional
    public CommentResponse save(long scheduleId, CommentCreateRequest request) {

        // DB에 일정이 존재하지 않는 경우
        Schedule schedule = scheduleService.findScheduleById(scheduleId);
        Comment comment = commentRepository.save(new Comment(request.getContent(), request.getUsername(), schedule));
        return CommentResponse.toDto(comment);
    }

    @Transactional
    public CommentResponse update(long scheduleId, long commentId, CommentUpdateRequest request) {
        // DB에 일정이 존재하지 않는 경우
        scheduleService.findScheduleById(scheduleId);
        // 해당 댓글이 DB에 존재하지 않는 경우
        Comment comment = findById(commentId);

        // 사용자가 일치하지 않는 경우
        if (!Objects.equals(comment.getUsername(), request.getUsername())) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }

        comment.update(request.getContent());
        return CommentResponse.toDto(comment);
    }

    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new DataNotFoundException("해당 댓글이 DB에 존재하지 않습니다."));
    }

    public void delete(long scheduleId, long commentId, String username) {
        // DB에 일정이 존재하지 않는 경우
        scheduleService.findScheduleById(scheduleId);
        // 해당 댓글이 DB에 존재하지 않는 경우
        Comment comment = findById(commentId);
        // 작성자가 동일하지 않는 경우
        if (!Objects.equals(comment.getUsername(), username)) {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }
    }
}