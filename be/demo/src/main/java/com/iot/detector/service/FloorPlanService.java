package com.iot.detector.service;

import com.iot.detector.entity.FloorPlan;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface FloorPlanService {
    FloorPlan saveFloorPlan(MultipartFile file, String name) throws IOException;
    FloorPlan getFloorPlan(Long id);
    FloorPlan deleteFloorPlan(Long id);
}
