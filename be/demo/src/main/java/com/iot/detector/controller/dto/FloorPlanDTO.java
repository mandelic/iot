package com.iot.detector.controller.dto;

import com.iot.detector.entity.FloorPlan;
import com.iot.detector.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter @AllArgsConstructor
public class FloorPlanDTO {
    private Long id;
    private UUID assetId;
    private String name;

    public FloorPlanDTO(FloorPlan floorPlan) {
        this.id = floorPlan.getId();
        this.name = floorPlan.getName();
        this.assetId = floorPlan.getAssetId();
    }

}
