package com.sparta.schedulemanagementappserver.exception;

public class ScheduleAlreadyDeletedException extends RuntimeException {
    public ScheduleAlreadyDeletedException(String message) {
        super(message);
    }
}
