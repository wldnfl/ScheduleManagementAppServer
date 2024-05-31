package com.sparta.schedulemanagementappserver.controller;

import com.sparta.schedulemanagementappserver.dto.CommentCreateRequest;
import com.sparta.schedulemanagementappserver.dto.CommentResponse;
import com.sparta.schedulemanagementappserver.dto.CommentUpdateRequest;
import com.sparta.schedulemanagementappserver.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedule/{scheduleId}/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> create(
            @PathVariable(name = "scheduleId") long scheduleId,
            @RequestBody CommentCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                commentService.save(scheduleId, request)
        );
    }

    @PatchMapping("{commentId}")
    public ResponseEntity<CommentResponse> update(
            @PathVariable(name = "scheduleId") long scheduleId,
            @PathVariable(name = "commentId") long commentId,
            @RequestBody CommentUpdateRequest request) {
        return ResponseEntity.ok().body(commentService.update(scheduleId, commentId, request));
    }

    @DeleteMapping("{commentId}")
    public ResponseEntity<String> delete(
            @PathVariable(name = "scheduleId") long scheduleId,
            @PathVariable(name = "commentId") long commentId,
            @RequestBody String username) {

        commentService.delete(scheduleId, commentId, username);
        return ResponseEntity.ok().body("성공적으로 댓글 삭제!");
    }
}
