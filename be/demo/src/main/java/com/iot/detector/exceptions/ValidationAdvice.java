package com.iot.detector.exceptions;

import com.iot.detector.exceptions.json.ValidationErrorJson;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ValidationAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorJson> methodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        String path = ((ServletWebRequest)request).getRequest().getRequestURI();
        BindingResult result = ex.getBindingResult();
        List<String> fieldErrors = result.getFieldErrors().stream().map(err -> err.getDefaultMessage()).collect(Collectors.toList());
        return ResponseEntity.badRequest().body(new ValidationErrorJson(path, fieldErrors));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ValidationErrorJson> methodArgumentNotUniqueException(DataIntegrityViolationException ex, WebRequest request) {
        String path = ((ServletWebRequest)request).getRequest().getRequestURI();
        String message = ex.getMessage();
        if (message.contains("users_email_key")) message = "Adresa e-pošte već postoji.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ValidationErrorJson(path, Arrays.asList(message)));
    }

}