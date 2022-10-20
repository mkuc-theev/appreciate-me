package com.appreciateme.tagservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class TagServiceMain {
    public static void main(String[] args) {
        SpringApplication tagService = new SpringApplication(TagServiceMain.class);
        tagService.setDefaultProperties(Collections.singletonMap("server.port", "8005"));
        tagService.run(args);
    }
}
