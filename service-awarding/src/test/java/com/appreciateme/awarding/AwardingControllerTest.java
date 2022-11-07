package com.appreciateme.awarding;

import com.appreciateme.awarding.controller.AwardingController;
import com.appreciateme.awarding.controller.AwardingService;
import com.appreciateme.awarding.exception.AwardingNotFoundException;
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
import java.util.Objects;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
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

    @Test
    @DisplayName("[ 2] given user id - when GET /awardings/:id - then return status OK")
    void givenAwarding_whenGETAwardingsId_thenReturnStatusOK()
            throws Exception {

        final String id = AWARDING_1.getUserId();
        final String endpoint = String.format("/%s/%s", DOMAIN, id);

        when(service.getById(eq(id)))
                .thenReturn(AWARDING_1);

        mockMvc.perform(get(endpoint)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.userId").value(AWARDING_1.getUserId()))
                .andExpect(jsonPath("$.rewards", hasSize(AWARDING_1.getRewards().size())));
    }

    @Test
    @DisplayName("[ 3] given id of user not present in Awardings - when GET /awardings/:id - then return status NOT_FOUND")
    void givenIdOfUserNotPresentInAwardings_whenGETAwardingsId_then()
            throws Exception {

        final String id = "huehue";
        final String endpoint = String.format("/%s/%s", DOMAIN, id);
        final String expectedMessage = String.format("Reward with ID = %s not found", id);

        when(service.getById(id))
                .thenThrow(new AwardingNotFoundException(id));

        mockMvc.perform(get(endpoint)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        assertTrue(
                                result.getResolvedException() instanceof AwardingNotFoundException))
                .andExpect(result ->
                        assertEquals(
                                expectedMessage,
                                Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    @DisplayName("[ 4] given none - when DELETE /awardings - then return status OK")
    void givenNone_whenDELETEAwardings_thenReturnStatusOK()
            throws Exception {

        final String endpoint = String.format("/%s", DOMAIN);

        mockMvc.perform(delete(endpoint)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[ 5] given Awarding - when DELETE /awardings/:id - then return status OK")
    void givenAwarding_whenDELETEAwardingsId_thenReturnStatusOK()
            throws Exception {

        final String id = AWARDING_DTO_1.getUserId();
        final String endpoint = String.format("/%s/%s", DOMAIN, id);

        mockMvc.perform(delete(endpoint)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[ 6] given id of user not present in awardings - when DELETE /awardings/:id - then return status NOT_FOUND")
    void givenIdOfUserNotPresentInAwardings_whenDELETEAwardingsId_thenThrowAwardingNotFoundException()
            throws Exception {

        final String id = "huehue";
        final String endpoint = String.format("/%s/%s", DOMAIN, id);
        final String expectedMessage = String.format("Reward with ID = %s not found", id);

        when(service.delete(id))
                .thenThrow(new AwardingNotFoundException(id));

        mockMvc.perform(delete(endpoint))
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        assertTrue(
                                result.getResolvedException() instanceof AwardingNotFoundException))
                .andExpect(result ->
                        assertEquals(
                                expectedMessage,
                                Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

}
