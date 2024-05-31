package com.sparta.schedulemanagementappserver.controller;

import com.sparta.schedulemanagementappserver.dto.CommentRequestDto;
import com.sparta.schedulemanagementappserver.dto.CommentResponseDto;
import com.sparta.schedulemanagementappserver.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comment") // 댓글 등록
    public CommentResponseDto createComment(HttpServletRequest request, @PathVariable String schedule, @RequestBody CommentRequestDto commentdto) {
        return commentService.createComment(request, schedule, commentdto);
    }

    @PutMapping("/comment/{comment_id}") // 댓글 수정
    public String updateComment(HttpServletRequest request, @PathVariable String schedule, @RequestBody CommentRequestDto commentdto, @PathVariable Long comment_id) {
        return commentService.updateComment(request, schedule, commentdto, comment_id);
    }

    @DeleteMapping("/comment/{comment_id}") // 댓글 삭제
    public String deleteComment(HttpServletRequest request, @PathVariable String schedule, @PathVariable Long comment_id) {
        return commentService.deleteComment(request, schedule, comment_id);
    }
}