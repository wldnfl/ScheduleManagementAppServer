package com.sparta.schedulemanagementappserver.exception;

import com.sparta.schedulemanagementappserver.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCommentNotFoundException(CommentNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedCommentActionException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedCommentActionException(UnauthorizedCommentActionException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.FORBIDDEN.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorResponse> handleInvalidInputException(InvalidInputException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDataNotFoundException(DataNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ErrorResponse> handlePasswordMismatchException(PasswordMismatchException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ScheduleAlreadyDeletedException.class)
    public ResponseEntity<ErrorResponse> handleScheduleAlreadyDeletedException(ScheduleAlreadyDeletedException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.GONE.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.GONE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}