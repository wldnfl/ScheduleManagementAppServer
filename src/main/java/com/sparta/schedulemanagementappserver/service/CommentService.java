package com.sparta.schedulemanagementappserver.service;

import com.sparta.schedulemanagementappserver.dto.CommentRequestDto;
import com.sparta.schedulemanagementappserver.dto.CommentResponseDto;
import com.sparta.schedulemanagementappserver.entity.Comment;
import com.sparta.schedulemanagementappserver.entity.Schedule;
import com.sparta.schedulemanagementappserver.repository.CommentRepository;
import com.sparta.schedulemanagementappserver.repository.ScheduleRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    public CommentResponseDto createComment(HttpServletRequest request, String schedule, CommentRequestDto commentDto) {
        // 일정 ID로 일정 찾기
        Long scheduleId = Long.parseLong(schedule);
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(scheduleId);

        if (optionalSchedule.isPresent()) {
            Schedule scheduleEntity = optionalSchedule.get();

            // 새로운 댓글 생성
            Comment comment = new Comment();
            comment.setContent(commentDto.getContent());
            comment.setUsername(commentDto.getUsername());
            comment.setCreatedAt(LocalDateTime.now());
            comment.setSchedule(scheduleEntity);

            // 댓글을 레포지토리에 저장
            commentRepository.save(comment);

            return new CommentResponseDto(comment);
        } else {
            throw new IllegalArgumentException("일정을 찾을 수 없습니다.");
        }
    }

    @Transactional
    public String updateComment(HttpServletRequest request, String schedule, CommentRequestDto commentDto, Long commentId) {
        // 댓글 ID로 댓글 찾기
        Optional<Comment> optionalComment = commentRepository.findById(commentId);

        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();

            // 댓글 내용 업데이트
            comment.updateComment(commentDto);

            return "댓글이 성공적으로 업데이트되었습니다.";
        } else {
            throw new IllegalArgumentException("댓글을 찾을 수 없습니다.");
        }
    }

    @Transactional
    public String deleteComment(HttpServletRequest request, String schedule, Long commentId) {
        // 댓글 ID로 댓글 찾기
        Optional<Comment> optionalComment = commentRepository.findById(commentId);

        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();

            // 댓글 삭제
            commentRepository.delete(comment);

            return "댓글이 성공적으로 삭제되었습니다.";
        } else {
            throw new IllegalArgumentException("댓글을 찾을 수 없습니다.");
        }
    }
}
