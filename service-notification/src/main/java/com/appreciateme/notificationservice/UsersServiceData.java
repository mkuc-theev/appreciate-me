package com.appreciateme.notificationservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class UsersServiceData {
    @Value("${services.users.host}")
    private  String host;
    @Value("${services.users.port}")
    private String port;
    @Bean
    public String getURI() {
        return "http://%s:%s".formatted(host, port);
    }
}