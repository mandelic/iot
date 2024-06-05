package com.iot.detector.controller;

import com.iot.detector.service.ThingsBoardRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/thingsboard")
public class ThingsBoardController {
    @Autowired
    ThingsBoardRestClient thingsBoardRestClient;

    @GetMapping
    public void initThingsBoard() {
        thingsBoardRestClient.initClient();
    }
}
