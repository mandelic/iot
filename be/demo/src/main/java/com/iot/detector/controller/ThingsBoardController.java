package com.iot.detector.controller;

import com.iot.detector.service.ThingsBoardRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thingsboard.server.common.data.Device;
import org.thingsboard.server.common.data.asset.Asset;

import java.util.List;

@RestController
@RequestMapping("/v1/thingsboard")
public class ThingsBoardController {
    @Autowired
    ThingsBoardRestClient thingsBoardRestClient;

    @PostMapping("/init")
    public void initThingsBoard() {
        thingsBoardRestClient.initClient();
    }

    @PostMapping("/disconnect")
    public void disconnectThingsBoard() {
        thingsBoardRestClient.disconnectClient();
    }

    @GetMapping("/asset/{id}")
    public String fetchAsset(@PathVariable String id) {
        return thingsBoardRestClient.fetchAsset(id);
    }

    @GetMapping("/tenant-assets")
    public List<Asset> fetchTenantAssets() {
        return thingsBoardRestClient.fetchTenantAssets();
    }

    @GetMapping("/device/{id}")
    public String fetchDevice(@PathVariable String id) {
        return thingsBoardRestClient.fetchDevice(id);
    }

    @GetMapping("/tenant-devices")
    public List<Device> fetchTenantDevices() {
        return thingsBoardRestClient.fetchTenantDevices();
    }
}
