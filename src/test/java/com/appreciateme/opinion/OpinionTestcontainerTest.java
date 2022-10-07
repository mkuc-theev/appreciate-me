package com.appreciateme.opinion;

import com.appreciateme.opinion.model.Opinion;
import com.appreciateme.opinion.repository.OpinionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@AutoConfigureMockMvc
public class OpinionTestcontainerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OpinionRepository repository;

    @Value("{$spring.data.mongodb.image}")
    private static String MONGODB_DOCKER_IMAGE;

    @Container
    private final static MongoDBContainer container = new MongoDBContainer("mongo:6.0.2");

    @DynamicPropertySource
    private static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.data.mongodb.uri", container::getReplicaSetUrl);
    }

    private static Opinion getOpinion() {
        return Opinion.builder()
                .id("633ca00a45ef711325f9d80f")
                .opinionUserID("atyranski")
                .reviewedUserID("pbogdan")
                .predefinedMessageID("goodJob1")
                .opinionMessage("a really nice byczeq")
                .build();
    }

    @Test
    public void test()
            throws Exception {

        Opinion opinion = getOpinion();
        String opinionString = objectMapper.writeValueAsString(opinion);

        mockMvc.perform(post("/opinions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(opinionString))
                .andExpect(status().isCreated());

        assertEquals(1, repository.findAll().size());
    }

}
