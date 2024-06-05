package com.iot.detector.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomMessageException extends RuntimeException{
    private String message;

    public CustomMessageException(String message) { this.message = message;}
}