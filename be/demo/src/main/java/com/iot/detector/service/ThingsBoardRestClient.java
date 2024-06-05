package com.iot.detector.service;

import org.springframework.stereotype.Service;
import org.thingsboard.rest.client.RestClient;
import org.thingsboard.server.common.data.Device;
import org.thingsboard.server.common.data.asset.Asset;
import org.thingsboard.server.common.data.id.AssetId;
import org.thingsboard.server.common.data.id.DeviceId;

import java.util.Optional;
import java.util.UUID;

@Service
public class ThingsBoardRestClient {
    String url = "http://161.53.19.19:45080/";
    String username = "damjan.sirovatka@fer.hr";
    String password = "U&6WcT9T$~:TXpX";

    public void initClient() {
        RestClient client = new RestClient(url);
        client.login(username, password);
        Optional<Asset> asset = client.getAssetById(new AssetId(UUID.fromString("bc3f1bd0-1eae-11ef-a963-a37ba3a57ce2")));
        Optional<Device> device = client.getDeviceById(new DeviceId(UUID.fromString("d1e281c0-1eae-11ef-a963-a37ba3a57ce2")));
        asset.ifPresent(value -> System.out.println(value.getName()));
        device.ifPresent(value -> System.out.println(value.getName()));
    }
}
