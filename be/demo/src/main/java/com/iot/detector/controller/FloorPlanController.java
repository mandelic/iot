package com.iot.detector.controller;

import com.iot.detector.controller.dto.BasicUserDTO;
import com.iot.detector.controller.dto.FloorPlanDTO;
import com.iot.detector.controller.dto.UserGroupDetailsDTO;
import com.iot.detector.entity.FloorPlan;
import com.iot.detector.entity.User;
import com.iot.detector.entity.UserGroup;
import com.iot.detector.exceptions.EntityIdNotFoundException;
import com.iot.detector.service.FloorPlanService;
import com.iot.detector.service.ThingsBoardRestClient;
import com.iot.detector.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thingsboard.server.common.data.id.AssetId;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/floor-plans")
public class FloorPlanController {

    @Autowired
    private FloorPlanService floorPlanService;
    @Autowired
    private ThingsBoardRestClient thingsBoardRestClient;
    @Autowired
    private UserService userService;

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

    @GetMapping
    public List<FloorPlanDTO> getAllFloorPlans() {
        return floorPlanService.getAllFloorPlans().stream().map(FloorPlanDTO::new).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFloorPlan(@PathVariable Long id) {
        FloorPlan floorPlan = floorPlanService.deleteFloorPlan(id);
        AssetId assetId = new AssetId(floorPlan.getAssetId());
        thingsBoardRestClient.deleteAsset(assetId);
        return ResponseEntity.ok("Floor plan with id " + id + " deleted successfully.");
    }

    @PostMapping("/{id}")
    public ArrayList<BasicUserDTO> addUsersFloorPlan(@PathVariable Long id, @RequestBody ArrayList<Long> userIds) {
        FloorPlan floorPlan = floorPlanService.getFloorPlan(id);
        ArrayList<BasicUserDTO> addedUsers = new ArrayList<>();
        for (Long userId : userIds) {
            User user = userService.findById(userId);
            floorPlan.addUser(user);
            addedUsers.add(new BasicUserDTO(user));
            userService.updateUser(user);
        }
        floorPlanService.updateFloorPlan(floorPlan);
        return addedUsers;
    }
}
