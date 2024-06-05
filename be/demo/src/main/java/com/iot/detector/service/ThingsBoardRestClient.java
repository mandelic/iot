package com.iot.detector.service;

import com.iot.detector.exceptions.CustomMessageException;
import org.springframework.stereotype.Service;
import org.thingsboard.rest.client.RestClient;
import org.thingsboard.server.common.data.Device;
import org.thingsboard.server.common.data.User;
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
    String url = "http://161.53.19.19:45080/";
    String username = "damjan.sirovatka@fer.hr";
    String password = "U&6WcT9T$~:TXpX";
    RestClient client;
    boolean isConnected = false;

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

    public String fetchAsset(String assetId) {
        checkConnection();
        Optional<Asset> asset = client.getAssetById(new AssetId(UUID.fromString(assetId)));
        return asset.map(Asset::getName).orElse(null);
    }

    public String fetchDevice(String deviceId) {
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

    private void checkConnection() {
        if (!isConnected) {
            throw new CustomMessageException("ThingsBoardRestClient is not connected", 1);
        }
    }
}
