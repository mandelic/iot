package com.iot.detector.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TokenDTO {
    private String message;
    private String role;
    private String token;
    private Long userId;
}