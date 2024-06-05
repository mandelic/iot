package com.iot.detector.exceptions;

import com.iot.detector.exceptions.json.ErrorJson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserIdNotFoundException.class)
    public ResponseEntity<ErrorJson> handleAuthorIdNotFoundException(UserIdNotFoundException exception, WebRequest request) {
        String path = ((ServletWebRequest)request).getRequest().getRequestURI();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorJson(path, exception.getMessage()));
    }

    @ExceptionHandler(EntityIdNotFoundException.class)
    public ResponseEntity<ErrorJson> handleEntityIdNotFoundException(EntityIdNotFoundException exception, WebRequest request) {
        String path = ((ServletWebRequest)request).getRequest().getRequestURI();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorJson(path, exception.getMessage()));
    }

    @ExceptionHandler(CustomMessageException.class)
    public ResponseEntity<ErrorJson> handleCustomMessage(CustomMessageException exception, WebRequest request) {
        String path = ((ServletWebRequest)request).getRequest().getRequestURI();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorJson(path, exception.getMessage(), exception.getErrorCode()));
    }

}
