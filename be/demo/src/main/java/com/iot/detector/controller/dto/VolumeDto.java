package com.iot.detector.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;

@Getter @AllArgsConstructor @NoArgsConstructor
public class VolumeDto {
    private ArrayList<VolumeItem> volume;

    @Getter @Setter
    private static class VolumeItem {
        VolumeItem(long ts, String value) {
            this.timestamp = new Date(ts);
            this.value = value;
        }
        private Date timestamp;
        private String value;
    }
}


