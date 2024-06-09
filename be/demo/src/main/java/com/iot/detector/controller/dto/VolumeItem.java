package com.iot.detector.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class VolumeItem {
    VolumeItem(long ts, String value) {
        this.timestamp = new Date(ts);
        this.value = value;
    }
    private Date timestamp;
    private String value;
}
