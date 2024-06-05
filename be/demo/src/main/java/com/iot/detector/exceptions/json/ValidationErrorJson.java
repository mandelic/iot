package com.iot.detector.exceptions.json;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter @NoArgsConstructor
public class ValidationErrorJson {

    private String path;
    private List<String> errors;
    private List<String> fields;
    public ValidationErrorJson(String path, List<String> errors) {
        this.path = path;
        this.errors = errors;
        this.fields = errors.stream().map(err -> err.split(" ")[0]).collect(Collectors.toList());

    }
}