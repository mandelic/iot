package com.iot.detector.service;

import com.iot.detector.controller.dto.LoginRequestDto;
import com.iot.detector.controller.dto.ThingsBoardTokenDto;
import com.iot.detector.controller.dto.VolumeDto;
import com.iot.detector.exceptions.CustomMessageException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.thingsboard.rest.client.RestClient;
import org.thingsboard.server.common.data.Device;
import org.thingsboard.server.common.data.asset.Asset;
import org.thingsboard.server.common.data.id.AssetId;
import org.thingsboard.server.common.data.id.DeviceId;
import org.thingsboard.server.common.data.page.PageData;
import org.thingsboard.server.common.data.page.PageLink;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ThingsBoardRestClient {
    String url = "http://161.53.19.19:45080";
    String username = "damjan.sirovatka@fer.hr";
    String password = "U&6WcT9T$~:TXpX";
    RestClient client;
    boolean isConnected = false;

    private final RestTemplate restTemplate;

    public ThingsBoardRestClient() {
        this.restTemplate = new RestTemplate();
    }

    public void initClient() {
        client = new RestClient(url);
        client.login(username, password);
        isConnected = true;
    }

    public void disconnectClient() {
        client.logout();
        client.close();
        isConnected = false;
    }

    public String fetchAssetById(String assetId) {
        checkConnection();
        Optional<Asset> asset = client.getAssetById(new AssetId(UUID.fromString(assetId)));
        return asset.map(Asset::getName).orElse(null);
    }

    public String fetchDeviceById(String deviceId) {
        checkConnection();
        Optional<Device> device = client.getDeviceById(new DeviceId(UUID.fromString(deviceId)));
        return device.map(Device::getName).orElse(null);
    }

    public List<Asset> fetchTenantAssets() {
        checkConnection();
        PageData<Asset> pageData;
        PageLink pageLink = new PageLink(10);
        List<Asset> list = new ArrayList<>();
        do {
            pageData = client.getTenantAssets(pageLink, "");
            list.addAll(pageData.getData());
            pageLink = pageLink.nextPageLink();
        } while (pageData.hasNext());

        return list;
    }

    public List<Device> fetchTenantDevices() {
        checkConnection();
        PageData<Device> pageData;
        PageLink pageLink = new PageLink(10);
        List<Device> list = new ArrayList<>();
        do {
            pageData = client.getTenantDevices("", pageLink);
            list.addAll(pageData.getData());
            pageLink = pageLink.nextPageLink();
        } while (pageData.hasNext());

        return list;
    }

    public ThingsBoardTokenDto fetchJwtToken() {
        String loginUrl = url + "/api/auth/login";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");
        LoginRequestDto loginRequest = new LoginRequestDto("damjan.sirovatka@fer.hr", "U&6WcT9T$~:TXpX");
        HttpEntity<LoginRequestDto> request = new HttpEntity<>(loginRequest, headers);
        return restTemplate.exchange(loginUrl, HttpMethod.POST, request, ThingsBoardTokenDto.class).getBody();
    }

    public VolumeDto fetchTelemetryData() {
        checkConnection();
        String jwtToken = fetchJwtToken().getToken();
        String telemetryUrl = url + "/api/plugins/telemetry/DEVICE/d1e281c0-1eae-11ef-a963-a37ba3a57ce2/values/timeseries?keys=volume";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");
        headers.set("X-Authorization", "Bearer " + jwtToken);
        HttpEntity<String> request = new HttpEntity<>(headers);
        return restTemplate.exchange(telemetryUrl, HttpMethod.GET, request, VolumeDto.class).getBody();
    }


    private void checkConnection() {
        if (!isConnected) {
            throw new CustomMessageException("ThingsBoardRestClient is not connected", 1);
        }
    }
}
