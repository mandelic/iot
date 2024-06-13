package com.iot.detector.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.iot.detector.controller.dto.*;
import com.iot.detector.exceptions.CustomMessageException;
import com.iot.detector.exceptions.EntityIdNotFoundException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.thingsboard.rest.client.RestClient;
import org.thingsboard.server.common.data.Device;
import org.thingsboard.server.common.data.EntityType;
import org.thingsboard.server.common.data.asset.Asset;
import org.thingsboard.server.common.data.device.DeviceSearchQuery;
import org.thingsboard.server.common.data.device.data.DeviceData;
import org.thingsboard.server.common.data.id.AssetId;
import org.thingsboard.server.common.data.id.DeviceId;
import org.thingsboard.server.common.data.id.EntityId;
import org.thingsboard.server.common.data.page.PageData;
import org.thingsboard.server.common.data.page.PageLink;
import org.thingsboard.server.common.data.query.*;
import org.thingsboard.server.common.data.relation.EntityRelation;
import org.thingsboard.server.common.data.relation.EntitySearchDirection;
import org.thingsboard.server.common.data.relation.RelationTypeGroup;
import org.thingsboard.server.common.data.relation.RelationsSearchParameters;

import java.util.*;

@Service
public class ThingsBoardRestClient {
    String url = "http://161.53.19.19:45080";
    String username = "damjan.sirovatka@fer.hr";
    String password = "U&6WcT9T$~:TXpX";
    RestClient client;
    boolean isConnected = true;
    //int VOLUME_LIMIT = 500;
    String CONTAINS = "Contains";

    private final RestTemplate restTemplate;

    public ThingsBoardRestClient() {
        this.restTemplate = new RestTemplate();
        client = new RestClient(url);
        client.login(username, password);
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

    public List<Device> fetchAssetDevices(UUID id) {
        checkConnection();
        DeviceSearchQuery query = new DeviceSearchQuery();
        query.setDeviceTypes(List.of("default"));
        query.setRelationType(CONTAINS);
        RelationsSearchParameters parameters = new RelationsSearchParameters(id, EntityType.ASSET, EntitySearchDirection.FROM, RelationTypeGroup.COMMON, 1073741824, true);
        query.setParameters(parameters);
        return client.findByQuery(query);
    }

    public Device createDevice(DeviceDto deviceDto, UUID id) {
        checkConnection();
        Device device = new Device();
        AssetId assetId = new AssetId(id);
        device.setName(deviceDto.getDeviceName());
        device.setLabel(deviceDto.getDeviceLabel());
        ObjectMapper mapper = new ObjectMapper();
        DeviceDto.Point point = deviceDto.getPoint();
        String builder = point.getX() +
                "," +
                point.getY();
        ObjectNode additionalInfoNode = mapper.createObjectNode().put("description", builder);
        device.setAdditionalInfo(additionalInfoNode);
        Device newDevice = client.createDevice(device);
        EntityRelation relation = new EntityRelation();
        relation.setTypeGroup(RelationTypeGroup.COMMON);
        relation.setType(CONTAINS);
        relation.setTo(newDevice.getId());
        relation.setFrom(assetId);
        client.saveRelation(relation);
        return newDevice;
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

    public VolumeDto fetchTelemetryData(String id) {
        checkConnection();
        String jwtToken = fetchJwtToken().getToken();
        String telemetryUrl = url + "/api/plugins/telemetry/DEVICE/" + id + "/values/timeseries?keys=volume";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");
        headers.set("X-Authorization", "Bearer " + jwtToken);
        HttpEntity<String> request = new HttpEntity<>(headers);
        VolumeDto volumeData = restTemplate.exchange(telemetryUrl, HttpMethod.GET, request, VolumeDto.class).getBody();
        return volumeData;
    }

    public AssetId createAsset(String name) {
        checkConnection();
        Asset asset = new Asset();
        asset.setName(name);
        asset.setLabel(name);
        try {
            Asset newAsset = client.createAsset(asset);
            return newAsset.getId();
        } catch (Exception e) {
            throw new CustomMessageException("Creating new asset with name " + name + " failed for reason " + e.getMessage(), -1);
        }
    }

    public void deleteAsset(AssetId assetId) {
        checkConnection();
        try {
            client.deleteAsset(assetId);
        } catch (Exception e) {
            throw new CustomMessageException("Deleting asset with id " + assetId + " failed for reason " + e.getMessage(), -1);
        }
    }

    private void checkConnection() {
        if (!isConnected) {
            initClient();
        }
    }
}
