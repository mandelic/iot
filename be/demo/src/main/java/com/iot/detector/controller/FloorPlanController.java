package com.iot.detector.controller;

import com.iot.detector.entity.FloorPlan;
import com.iot.detector.service.FloorPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/v1/floor-plans")
public class FloorPlanController {

    @Autowired
    private FloorPlanService floorPlanService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFloorPlan(@RequestParam("file") MultipartFile file, @RequestParam("name") String name) {
        try {
            FloorPlan floorPlan = floorPlanService.saveFloorPlan(file, name);
            return ResponseEntity.status(HttpStatus.OK).body("FloorPlan uploaded successfully: " + floorPlan.getId());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not upload the floorPlan: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getFloorPlan(@PathVariable Long id) {
        FloorPlan floorPlan = floorPlanService.getFloorPlan(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + floorPlan.getName() + "\"")
                .body(floorPlan.getData());
    }
}
