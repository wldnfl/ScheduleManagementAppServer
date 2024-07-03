package com.sparta.schedulemanagementappserver.service;

import com.sparta.schedulemanagementappserver.dto.CommentCreateRequest;
import com.sparta.schedulemanagementappserver.dto.CommentResponse;
import com.sparta.schedulemanagementappserver.dto.CommentUpdateRequest;
import com.sparta.schedulemanagementappserver.entity.Comment;
import com.sparta.schedulemanagementappserver.entity.Schedule;
import com.sparta.schedulemanagementappserver.exception.*;
import com.sparta.schedulemanagementappserver.repository.CommentRepository;
import com.sparta.schedulemanagementappserver.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleService scheduleService;
    private final ScheduleRepository scheduleRepository;

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
            throw new UnauthorizedCommentActionException("작성자만 수정할 수 있습니다.");
        }

        comment.update(request.getContent());
        return CommentResponse.toDto(comment);
    }

    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("해당 댓글이 DB에 존재하지 않습니다."));
    }

    public void delete(Long scheduleId, Long commentId, String username) {
        if (scheduleId == null || commentId == null) {
            throw new InvalidInputException("댓글 혹은 일정의 아이디가 없습니다.");
        }

        // DB에 일정이 존재하지 않는 경우
        scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException("해당 id에 맞는 일정 데이터가 없습니다. 아이디 : " + scheduleId));

        // 해당 댓글이 DB에 존재하지 않는 경우
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("해당 댓글이 DB에 존재하지 않습니다."));

        if (!Objects.equals(comment.getUsername(), username)) {
            throw new UnauthorizedCommentActionException("사용자가 일치하지 않습니다.");
        }

        commentRepository.delete(comment);
    }
}