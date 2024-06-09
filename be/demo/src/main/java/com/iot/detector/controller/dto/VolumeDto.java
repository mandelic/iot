package com.iot.detector.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;


@Getter @AllArgsConstructor @NoArgsConstructor
public class VolumeDto {
    private ArrayList<VolumeItem> volume;
}


