package com.iot.detector.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityIdNotFoundException extends RuntimeException{
    private String message;

    public EntityIdNotFoundException(String entity, Long id) { message = entity + " with ID: " + id + " not found.";}
}