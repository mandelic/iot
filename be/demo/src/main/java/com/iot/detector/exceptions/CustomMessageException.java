package com.iot.detector.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomMessageException extends RuntimeException{
    private String message;
    private int errorCode;

    public CustomMessageException(String message, int errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }
}