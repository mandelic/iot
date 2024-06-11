package com.iot.detector.controller;

import com.iot.detector.controller.dto.DeviceDto;
import com.iot.detector.controller.dto.ThingsBoardTokenDto;
import com.iot.detector.controller.dto.VolumeDto;
import com.iot.detector.service.ThingsBoardRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thingsboard.server.common.data.Device;
import org.thingsboard.server.common.data.asset.Asset;

import java.util.List;
import java.util.UUID;

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
        return thingsBoardRestClient.fetchAssetById(id);
    }

    @GetMapping("/tenant-assets")
    public List<Asset> fetchTenantAssets() {
        return thingsBoardRestClient.fetchTenantAssets();
    }

    @GetMapping("/device/{id}")
    public String fetchDevice(@PathVariable String id) {
        return thingsBoardRestClient.fetchDeviceById(id);
    }

    @GetMapping("/tenant-devices")
    public List<Device> fetchTenantDevices() {
        return thingsBoardRestClient.fetchTenantDevices();
    }

    @GetMapping("/jwt-token")
    public ThingsBoardTokenDto fetchJwtToken() {
        return thingsBoardRestClient.fetchJwtToken();
    }

    @GetMapping("/telemetry")
    public VolumeDto fetchTelemetry() {
        return thingsBoardRestClient.fetchTelemetryData();
    }

    @GetMapping("/devices/{assetId}")
    public List<Device> fetchAssetDevices(@PathVariable UUID assetId) {
        return thingsBoardRestClient.fetchAssetDevices(assetId);
    }

    @PostMapping("/devices/{assetId}")
    public Device createDevice(@RequestBody DeviceDto device, @PathVariable UUID assetId) {
        return thingsBoardRestClient.createDevice(device, assetId);
    }

}
