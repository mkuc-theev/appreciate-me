package com.appreciateme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class NotificationService {

    public static void main(String[] args) {
        SpringApplication notificationService = new SpringApplication(NotificationService.class);
        notificationService.setDefaultProperties(Collections.singletonMap("server.port", "8004"));
        notificationService.run(args);
    }

}
