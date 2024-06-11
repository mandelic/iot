package com.iot.detector.service.impl;

import com.iot.detector.entity.FloorPlan;
import com.iot.detector.exceptions.EntityIdNotFoundException;
import com.iot.detector.exceptions.UserIdNotFoundException;
import com.iot.detector.repository.FloorPlanRepository;
import com.iot.detector.service.FloorPlanService;
import com.iot.detector.service.ThingsBoardRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thingsboard.server.common.data.id.AssetId;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class FloorPlanServiceImpl implements FloorPlanService {

    @Autowired
    private FloorPlanRepository floorPlanRepository;
    @Autowired
    private ThingsBoardRestClient thingsBoardRestClient;

    public FloorPlan saveFloorPlan(MultipartFile file, String name) throws IOException {
        FloorPlan floorPlan = new FloorPlan();
        floorPlan.setName(name);
        floorPlan.setType(file.getContentType());
        floorPlan.setData(file.getBytes());
        AssetId assetId = thingsBoardRestClient.createAsset(name);
        floorPlan.setAssetId(assetId.getId());
        return floorPlanRepository.save(floorPlan);
    }

    public FloorPlan getFloorPlan(Long id) {
        return floorPlanRepository.findById(id).orElseThrow(() -> new EntityIdNotFoundException("FloorPlan", id));
    }

    public FloorPlan deleteFloorPlan(Long id) {
        FloorPlan floorPlan = getFloorPlan(id);
        floorPlanRepository.deleteById(id);
        return floorPlan;
    }

    public FloorPlan updateFloorPlan(FloorPlan floorPlan) {
        return floorPlanRepository.save(floorPlan);
    }

    public List<FloorPlan> getAllFloorPlans() {
        return floorPlanRepository.findAll();
    }
}
