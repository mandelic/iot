package com.iot.detector.exceptions.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data @RequiredArgsConstructor
@AllArgsConstructor
public class ErrorJson {

    @NotNull
    private String path;
    @NotNull
    private String message;
    private int errorCode = 0;

}