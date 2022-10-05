package com.appreciateme.opinion;

import com.appreciateme.opinion.dto.OpinionDTO;
import com.appreciateme.opinion.dto.OpinionMapper;
import com.appreciateme.opinion.model.Opinion;
import com.appreciateme.opinion.repository.OpinionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@AutoConfigureMockMvc
public class OpinionTest {

    @Autowired
    private static Environment env;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OpinionRepository opinionRepository;

    @Container
    static private MongoDBContainer mongoDBContainer;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        String databaseVersion = env.getProperty("database.version");
        assert databaseVersion != null;

        mongoDBContainer = new MongoDBContainer(databaseVersion);
        dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void shouldCreateOpinion()
            throws Exception {

        Opinion opinion = getOpinion();
        OpinionDTO opinionDTO = OpinionMapper.toDto(opinion);

        String opinionString = objectMapper.writeValueAsString(opinionDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/opinions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(opinionString))
                .andExpect(status().isCreated());

        assertEquals(1, opinionRepository.findAll().size());
    }

    private Opinion getOpinion() {
        return Opinion.builder()
                .id("someopinion1")
                .opinionUserID("atyranski")
                .reviewedUserID("pbogdan")
                .predefinedMessageID("coolGuy")
                .opinionMessage("Bardzo dobry byczeq")
                .build();
    }

}
