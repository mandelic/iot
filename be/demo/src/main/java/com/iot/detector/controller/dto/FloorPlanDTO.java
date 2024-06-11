package com.iot.detector.controller.dto;

import com.iot.detector.entity.FloorPlan;
import com.iot.detector.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class FloorPlanDTO {
    private Long id;
    private String name;

    public FloorPlanDTO(FloorPlan floorPlan) {
        this.id = floorPlan.getId();
        this.name = floorPlan.getName();
    }

}
