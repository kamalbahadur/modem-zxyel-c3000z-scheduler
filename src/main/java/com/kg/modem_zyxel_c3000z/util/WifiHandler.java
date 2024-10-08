package com.kg.modem_zyxel_c3000z.util;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WifiHandler {

    @Autowired MdnsUtility mdnsUtility;
    @Autowired RestTemplate restTemplate;

    public void connect() {
        if (mdnsUtility.getConnectUrl() != null) {
            restTemplate.getForObject(URI.create(mdnsUtility.getConnectUrl()),  String.class);
        }
    }

    public void disconnect() {
        if (mdnsUtility.getDisconnectUrl() != null) {
            restTemplate.getForObject(URI.create(mdnsUtility.getDisconnectUrl()),  String.class);
        }
    }
}
