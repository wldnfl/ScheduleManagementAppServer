package com.sparta.schedulemanagementappserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DataConflictException extends RuntimeException {
    public DataConflictException(String message) {
        super(message);
    }
}
