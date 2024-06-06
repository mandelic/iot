package com.iot.detector.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class ThingsBoardTokenDto {
    private String token;
    private String refreshToken;
}
