package com.appreciateme.awarding;

import com.appreciateme.awarding.controller.AwardingController;
import com.appreciateme.awarding.controller.AwardingService;
import com.appreciateme.awarding.model.Awarding;
import com.appreciateme.awarding.model.AwardingDTO;
import com.appreciateme.awarding.model.OwnedReward;
import com.appreciateme.awarding.model.OwnedRewardDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@WebMvcTest(AwardingController.class)
public class AwardingControllerTest {

    final String DOMAIN = "awardings";

    final OwnedReward OWNED_REWARD_1 = OwnedReward.builder()
            .rewardId("6351508c881167185d79e3d0")
            .companyName("Tektura Cafe")
            .isService(true)
            .value(1.0)
            .description("Darmowe szkolenie Latte Art")
            .dateFrom("2022-04-01 08:00:00")
            .dateTo("2022-05-01 08:00:00")
            .used(false)
            .build();

    final OwnedReward OWNED_REWARD_2 = OwnedReward.builder()
            .rewardId("6357c098efc10f0e8db69d20")
            .companyName("Żabka")
            .isService(false)
            .value(0.5)
            .description("Duży hot-dog za 50% taniej!")
            .dateFrom("2022-04-01 08:00:00")
            .dateTo("2022-05-01 08:00:00")
            .used(true)
            .build();

    final OwnedReward OWNED_REWARD_3 = OwnedReward.builder()
            .rewardId("6351508c881167185d79e3d0")
            .companyName("Castorama")
            .isService(false)
            .value(0.75)
            .description("Taker firmy LOOOL za 75% taniej!")
            .dateFrom("2022-04-01 08:00:00")
            .dateTo("2022-05-01 08:00:00")
            .used(false)
            .build();

    final OwnedRewardDTO OWNED_REWARD_DTO_1 = OwnedRewardDTO.builder()
            .rewardId("6351508c881167185d79e3d0")
            .companyName("Tektura Cafe")
            .isService(true)
            .value(1.0)
            .description("Darmowe szkolenie Latte Art")
            .dateFrom(1648792800000L)
            .dateTo(1651384800000L)
            .used(false)
            .build();

    final OwnedRewardDTO OWNED_REWARD_DTO_2 = OwnedRewardDTO.builder()
            .rewardId("6357c098efc10f0e8db69d20")
            .companyName("Żabka")
            .isService(false)
            .value(0.5)
            .description("Duży hot-dog za 50% taniej!")
            .dateFrom(1648792800000L)
            .dateTo(1651384800000L)
            .used(true)
            .build();

    final OwnedRewardDTO OWNED_REWARD_DTO_3 = OwnedRewardDTO.builder()
            .rewardId("6351508c881167185d79e3d0")
            .companyName("Castorama")
            .isService(false)
            .value(0.75)
            .description("Taker firmy LOOOL za 75% taniej!")
            .dateFrom(1648792800000L)
            .dateTo(1651384800000L)
            .used(false)
            .build();

    final Awarding AWARDING_1 = Awarding.builder()
            .userId("abaranski")
            .rewards(List.of(OWNED_REWARD_1, OWNED_REWARD_2))
            .build();

    final Awarding AWARDING_2 = Awarding.builder()
            .userId("pbogdan")
            .rewards(List.of(OWNED_REWARD_2, OWNED_REWARD_3))
            .build();

    final AwardingDTO AWARDING_DTO_1 = AwardingDTO.builder()
            .userId("abaranski")
            .rewards(List.of(OWNED_REWARD_DTO_1, OWNED_REWARD_DTO_2))
            .build();

    final AwardingDTO AWARDING_DTO_2 = AwardingDTO.builder()
            .userId("pbogdan")
            .rewards(List.of(OWNED_REWARD_DTO_2, OWNED_REWARD_DTO_3))
            .build();

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    AwardingService service;


    @Test
    @DisplayName("[ 1] given List<Awarding> - when GET /awardings - then return status OK")
    void givenListOfAwardings_whenGETAwardings_thenReturnStatusOK()
            throws Exception {

        final List<Awarding> awardings = List.of(AWARDING_1, AWARDING_2);
        final String endpoint = String.format("/%s", DOMAIN);

        when(service.getAll())
                .thenReturn(awardings);

        mockMvc.perform(get(endpoint)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(awardings.size())))
                .andExpect(jsonPath("$[0].userId").value(AWARDING_1.getUserId()))
                .andExpect(jsonPath("$[0].rewards", hasSize(AWARDING_1.getRewards().size())))
                .andExpect(jsonPath("$[1].userId").value(AWARDING_2.getUserId()))
                .andExpect(jsonPath("$[1].rewards", hasSize(AWARDING_2.getRewards().size())));
    }



}
