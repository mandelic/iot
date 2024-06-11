package com.iot.detector.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class DeviceDto {
    private String deviceName;
    private String deviceLabel;
    private Point point;
    @Getter
    public static class Point {
        private double x;
        private double y;
    }
}
