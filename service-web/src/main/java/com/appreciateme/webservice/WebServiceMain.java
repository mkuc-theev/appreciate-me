package com.appreciateme.webservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class WebServiceMain {
    public static void main(String[] args) {
        SpringApplication webService = new SpringApplication(WebServiceMain.class);
        webService.setDefaultProperties(Collections.singletonMap("server.port", "8000"));
        webService.run(args);
    }
}
