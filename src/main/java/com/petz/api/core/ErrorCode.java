package com.petz.api.core;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorCode {
	SERVER(1),
    GLOBAL(2),
    AUTHENTICATION(10), 
    JWT_TOKEN_EXPIRED(11);
    
    private int errorCode;

    private ErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @JsonValue
    public int getErrorCode() {
        return errorCode;
    }
}