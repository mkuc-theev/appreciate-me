package com.appreciateme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class PredefService {

    public static void main(String[] args) {
        SpringApplication prefefService = new SpringApplication(PredefService.class);
        prefefService.setDefaultProperties(Collections.singletonMap("server.port", "8003"));
        prefefService.run(args);
    }

}


