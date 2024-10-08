package com.kg.modem_zyxel_c3000z.util;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import javax.jmdns.impl.util.ByteWrangler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kg.modem_zyxel_c3000z.constant.EventType;

@Component
public class MdnsUtility {
    @Value("${server.port}")
    String serverPort;

    @Value("${spring.application.name}")
    private String applicationName;

    private JmDNS jmdns;

    private volatile String connectUrl;
    private volatile String disconnectUrl;

    @PostConstruct
    public void init() throws IOException {
        // Create a JmDNS instance
        jmdns = JmDNS.create(InetAddress.getLocalHost());

        // Register a listener
        jmdns.addServiceListener("_http._tcp.local.", new ServiceEventsListener());

        // Register this service
        ServiceInfo serviceInfo = ServiceInfo.create("_http._tcp.local.", applicationName,
                Integer.parseInt(serverPort), "");
        Map<String, String> properties = new HashMap<>();
        properties.put("DB Console", "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + serverPort + "/h2-console");
        properties.put("View", "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + serverPort + "/?operation=view");
        properties.put("Add/Update", "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + serverPort + "/?operation=update&day=MONDAY&hour=22&minute=30&connect=False");
        properties.put("Delete", "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + serverPort + "/?operation=delete&day=MONDAY&hour=22&minute=30");
        serviceInfo.setText(properties);
        jmdns.registerService(serviceInfo);
    }

    private void handleServiceEvent(ServiceEvent event, EventType eventType) {

        if (!"modem-zyxel-c3000z".equals(event.getName())) {
            return;
        }

        if (EventType.REMOVED == eventType) {
            this.connectUrl = null;
            this.disconnectUrl = null;
        }

        if (EventType.ADDED == eventType || EventType.RESOLVED == eventType) {
            this.connectUrl = event.getInfo().getPropertyString("connect");
            this.disconnectUrl = event.getInfo().getPropertyString("disconnect");
        }
    }

    public String getConnectUrl() {
        return connectUrl;
    }

    public String getDisconnectUrl() {
        return disconnectUrl;
    }

    private class ServiceEventsListener implements ServiceListener {
        @Override
        public void serviceAdded(ServiceEvent event) {
            handleServiceEvent(event, EventType.ADDED);
        }

        @Override
        public void serviceRemoved(ServiceEvent event) {
            handleServiceEvent(event, EventType.REMOVED);
        }

        @Override
        public void serviceResolved(ServiceEvent event) {
            handleServiceEvent(event, EventType.RESOLVED);
        }
    }
}
