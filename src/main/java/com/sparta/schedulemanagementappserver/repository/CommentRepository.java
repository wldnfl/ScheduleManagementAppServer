package com.sparta.schedulemanagementappserver.repository;

import com.sparta.schedulemanagementappserver.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}