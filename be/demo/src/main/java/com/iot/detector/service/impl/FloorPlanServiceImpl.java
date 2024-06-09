package com.iot.detector.service.impl;

import com.iot.detector.entity.FloorPlan;
import com.iot.detector.exceptions.EntityIdNotFoundException;
import com.iot.detector.exceptions.UserIdNotFoundException;
import com.iot.detector.repository.FloorPlanRepository;
import com.iot.detector.service.FloorPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class FloorPlanServiceImpl implements FloorPlanService {

    @Autowired
    private FloorPlanRepository floorPlanRepository;

    public FloorPlan saveFloorPlan(MultipartFile file, String name) throws IOException {
        FloorPlan floorPlan = new FloorPlan();
        floorPlan.setName(name);
        floorPlan.setType(file.getContentType());
        floorPlan.setData(file.getBytes());

        return floorPlanRepository.save(floorPlan);
    }

    public FloorPlan getFloorPlan(Long id) {
        return floorPlanRepository.findById(id).orElseThrow(() -> new EntityIdNotFoundException("FloorPlan", id));
    }
}
