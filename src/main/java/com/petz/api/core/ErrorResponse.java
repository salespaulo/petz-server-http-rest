package com.petz.api.core;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private final HttpStatus status;

    private final ErrorCode code;

    private final String message;

    private final LocalDateTime timestamp;

    protected ErrorResponse(final String message, final ErrorCode code, HttpStatus status) {
        this.message = message;
        this.code = code;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public static ErrorResponse of(final String message, final ErrorCode errorCode, HttpStatus status) {
        return new ErrorResponse(message, errorCode, status);
    }

}