package com.appreciateme.webservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MicroserviceData {
    @Value("${services.user.host}")
    private String userHost;
    @Value("${services.user.port}")
    private String userPort;
    @Value("${services.opinion.host}")
    private String opinionHost;
    @Value("${services.opinion.port}")
    private String opinionPort;
    @Value("${services.predef.host}")
    private String predefHost;
    @Value("${services.predef.port}")
    private String predefPort;
    @Value("${services.notification.host}")
    private String notificationHost;
    @Value("${services.notification.port}")
    private String notificationPort;
    @Value("${services.tag.post}")
    private String tagHost;
    @Value("${services.tag.port}")
    private String tagPort;

    public String getUserURI() {
        return "http://%s:%s".formatted(userHost, userPort);
    }

    public String getOpinionURI() {
        return "http://%s:%s".formatted(opinionHost, opinionPort);
    }

    public String getPredefURI() {
        return "http://%s:%s".formatted(predefHost, predefPort);
    }

    public String getNotificationURI() {
        return "http://%s:%s".formatted(notificationHost, notificationPort);
    }

    public String getTagURI() {
        return "http://%s:%s".formatted(tagHost, tagPort);
    }
}
