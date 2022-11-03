package com.appreciateme.reward;

import com.appreciateme.reward.controller.RewardController;
import com.appreciateme.reward.controller.RewardService;
import com.appreciateme.reward.exception.IncorrectRewardException;
import com.appreciateme.reward.exception.RewardNotFoundException;
import com.appreciateme.reward.model.Reward;
import com.appreciateme.reward.model.RewardCorrectnessStatus;
import com.appreciateme.reward.model.RewardDTO;
import com.appreciateme.reward.model.RewardUtils;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@WebMvcTest(RewardController.class)
public class RewardControllerTest {

    final String DATE_STRING = "2022-03-09 08:00:00";
    final long DATE_TIMESTAMP = 1646809200000L;
    final String DOMAIN = "rewards";

    final Reward REWARD_1 = Reward.builder()
            .companyName("Tektura Cafe")
            .isService(true)
            .value(1.0)
            .requiredOpinionAmount(3)
            .description("Darmowe szkolenie Latte Art")
            .dateFrom("2022-03-09 08:00:00")
            .dateTo("2023-06-09 08:00:00")
            .availabilityDays(30)
            .build();

    final Reward REWARD_2 = Reward.builder()
            .companyName("Żabka")
            .isService(false)
            .value(0.5)
            .requiredOpinionAmount(5)
            .description("Duży hot-dog za 50% taniej!")
            .dateFrom("2022-10-09 08:00:00")
            .dateTo("2022-10-20 08:00:00")
            .availabilityDays(7)
            .build();

    final Reward REWARD_3 = Reward.builder()
            .companyName("Castorama")
            .isService(false)
            .value(0.75)
            .requiredOpinionAmount(10)
            .description("Taker firmy LOOOL za 75% taniej!")
            .dateFrom("2022-04-09 08:00:00")
            .dateTo("2023-02-09 08:00:00")
            .availabilityDays(14)
            .build();

    final RewardDTO REWARD_DTO_1 = RewardDTO.builder()
            .id("6351508c881167185d79e3d0")
            .companyName("Tektura Cafe")
            .isService(true)
            .value(1.0)
            .requiredOpinionAmount(3)
            .description("Darmowe szkolenie Latte Art")
            .dateFrom(1646809200000L)
            .dateTo(1686290400000L)
            .availabilityDays(30)
            .build();

    final RewardDTO REWARD_DTO_2 = RewardDTO.builder()
            .id("6357c098efc10f0e8db69d20")
            .companyName("Żabka")
            .isService(false)
            .value(0.5)
            .requiredOpinionAmount(5)
            .description("Duży hot-dog za 50% taniej!")
            .dateFrom(1665295200000L)
            .dateTo(1666245600000L)
            .availabilityDays(7)
            .build();

    final RewardDTO REWARD_DTO_3 = RewardDTO.builder()
            .id("6358da3be4f5493ce09d28d4")
            .companyName("Castorama")
            .isService(false)
            .value(0.75)
            .requiredOpinionAmount(10)
            .description("Taker firmy LOOOL za 75% taniej!")
            .dateFrom(1649484000000L)
            .dateTo(1675926000000L)
            .availabilityDays(14)
            .build();

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    RewardService service;

    @Test
    @DisplayName("[ 1] given List<Reward> - when GET /rewards - then return status OK")
    void givenListOfReward_whenGETRewards_thenReturnStatusOK()
            throws Exception {

        final List<Reward> records = List.of(REWARD_1, REWARD_2, REWARD_3);
        final String endpoint = String.format("/%s", DOMAIN);

        when(service.getAll())
                .thenReturn(records);

        mockMvc.perform(get(endpoint)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(records.size())))
                .andExpect(jsonPath("$[0].id").value(REWARD_1.getId()))
                .andExpect(jsonPath("$[0].companyName").value(REWARD_1.getCompanyName()))
                .andExpect(jsonPath("$[0].value").value(REWARD_1.getValue()))
                .andExpect(jsonPath("$[0].requiredOpinionAmount").value(REWARD_1.getRequiredOpinionAmount()))
                .andExpect(jsonPath("$[0].description").value(REWARD_1.getDescription()))
                .andExpect(jsonPath("$[0].dateFrom").value(REWARD_1.getDateFrom()))
                .andExpect(jsonPath("$[0].dateTo").value(REWARD_1.getDateTo()))
                .andExpect(jsonPath("$[0].availabilityDays").value(REWARD_1.getAvailabilityDays()))
                .andExpect(jsonPath("$[0].service").value(REWARD_1.isService()))
                .andExpect(jsonPath("$[1].id").value(REWARD_2.getId()))
                .andExpect(jsonPath("$[2].id").value(REWARD_2.getId()));
    }

    @Test
    @DisplayName("[ 2] given Reward - when GET /rewards/:id - then return status OK")
    void givenReward_whenGETRewardsId_thenReturnStatusOK()
            throws Exception {

        final String id = REWARD_DTO_1.getId();
        final String endpoint = String.format("/%s/%s", DOMAIN, id);

        when(service.getById(id))
                .thenReturn(REWARD_1);

        mockMvc.perform(get(endpoint)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id").value(REWARD_1.getId()))
                .andExpect(jsonPath("$.companyName").value(REWARD_1.getCompanyName()))
                .andExpect(jsonPath("$.value").value(REWARD_1.getValue()))
                .andExpect(jsonPath("$.requiredOpinionAmount").value(REWARD_1.getRequiredOpinionAmount()))
                .andExpect(jsonPath("$.description").value(REWARD_1.getDescription()))
                .andExpect(jsonPath("$.dateFrom").value(REWARD_1.getDateFrom()))
                .andExpect(jsonPath("$.dateTo").value(REWARD_1.getDateTo()))
                .andExpect(jsonPath("$.availabilityDays").value(REWARD_1.getAvailabilityDays()))
                .andExpect(jsonPath("$.service").value(REWARD_1.isService()));
    }

    @Test
    @DisplayName("[ 3] given id of not existing Reward - when GET /rewards/:id - then throw RewardNotFoundException")
    void givenIdOfNotExistingReward_whenGETRewardsId_thenThrowRewardNotFoundException()
            throws Exception {

        final String id = "haha";
        final String endpoint = String.format("/%s/%s", DOMAIN, id);
        final String expectedMessage = String.format("Reward with ID = %s not found", id);

        when(service.getById(id))
                .thenThrow(new RewardNotFoundException(id));

        mockMvc.perform(get(endpoint)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        assertTrue(
                                result.getResolvedException() instanceof RewardNotFoundException))
                .andExpect(result ->
                        assertEquals(
                                expectedMessage,
                                Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    @DisplayName("[ 4] given List<Reward> - when GET /rewards/user/:id - then return status OK")
    void givenListOfReward_whenGETRewardsUserId_thenReturnStatusOK()
            throws Exception {

        final String id = "atyranski";
        final String endpoint = String.format("/%s/user/%s", DOMAIN, id);
        final List<Reward> rewards = List.of(REWARD_1, REWARD_2);

        when(service.getAllForUser(id))
                .thenReturn(rewards);

        mockMvc.perform(get(endpoint).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(rewards.size())))
                .andExpect(jsonPath("$[0].id").value(REWARD_1.getId()))
                .andExpect(jsonPath("$[0].companyName").value(REWARD_1.getCompanyName()))
                .andExpect(jsonPath("$[0].value").value(REWARD_1.getValue()))
                .andExpect(jsonPath("$[0].requiredOpinionAmount").value(REWARD_1.getRequiredOpinionAmount()))
                .andExpect(jsonPath("$[0].description").value(REWARD_1.getDescription()))
                .andExpect(jsonPath("$[0].dateFrom").value(REWARD_1.getDateFrom()))
                .andExpect(jsonPath("$[0].dateTo").value(REWARD_1.getDateTo()))
                .andExpect(jsonPath("$[0].availabilityDays").value(REWARD_1.getAvailabilityDays()))
                .andExpect(jsonPath("$[0].service").value(REWARD_1.isService()))
                .andExpect(jsonPath("$[1].id").value(REWARD_2.getId()));
    }

    @Test
    @DisplayName("[ 5] given Reward - when POST /rewards - then return status CREATED")
    void givenReward_whenPOSTRewards_thenReturnStatusCREATED()
            throws Exception{

        final String endpoint = String.format("/%s", DOMAIN);

        when(service.add(REWARD_1))
                .thenReturn(REWARD_DTO_1.getId());

        mockMvc.perform(post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(REWARD_1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$").value(REWARD_DTO_1.getId()));
    }

    @Test
    @DisplayName("[ 6] given incorrect reward - when POST /rewards - then throw IncorrectRewardException")
    void givenIncorrectReward_whenPOSTRewards_thenThrowIncorrectRewardException()
            throws Exception {

        final Reward incorrectReward = REWARD_1;
        incorrectReward.setAvailabilityDays(0);

        final String endpoint = String.format("/%s", DOMAIN);
        final String expectedMessage = "Incorrect Reward: availabilityDays is less than 1 (should be at least 1)";

        when(service.add(incorrectReward))
                .thenThrow(new IncorrectRewardException(RewardCorrectnessStatus.NONPOSITIVE_AVAILABILITY_DAYS));

        mockMvc.perform(post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incorrectReward)))
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertTrue(
                                result.getResolvedException() instanceof IncorrectRewardException))
                .andExpect(result ->
                        assertEquals(
                                expectedMessage,
                                Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    @DisplayName("[ 7] given reward - when PUT /rewards - then return status OK")
    void givenReward_whenPUTRewards_thenReturnStatusOK()
            throws Exception {

        final String endpoint = String.format("/%s", DOMAIN);
        final Reward rewardWithID = RewardUtils.mapToReward(REWARD_DTO_1);

        when(service.update(rewardWithID))
                .thenReturn(rewardWithID);

        mockMvc.perform(put(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rewardWithID)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id").value(rewardWithID.getId()))
                .andExpect(jsonPath("$.companyName").value(rewardWithID.getCompanyName()))
                .andExpect(jsonPath("$.value").value(rewardWithID.getValue()))
                .andExpect(jsonPath("$.requiredOpinionAmount").value(rewardWithID.getRequiredOpinionAmount()))
                .andExpect(jsonPath("$.description").value(rewardWithID.getDescription()))
                .andExpect(jsonPath("$.dateFrom").value(rewardWithID.getDateFrom()))
                .andExpect(jsonPath("$.dateTo").value(rewardWithID.getDateTo()))
                .andExpect(jsonPath("$.availabilityDays").value(rewardWithID.getAvailabilityDays()))
                .andExpect(jsonPath("$.service").value(rewardWithID.isService()));
    }

    @Test
    @DisplayName("[ 8] given id of not existing reward - when PUT /rewards - then throw RewardNotFoundException")
    void givenIdOfNotExistingReward_whenPutRewards_thenThrowRewardNotFoundException()
            throws Exception {

        final String id = "hehe";
        final String endpoint = String.format("/%s", DOMAIN);
        final String expectedMessage = String.format("Reward with ID = %s not found", id);
        final Reward rewardWithID = RewardUtils.mapToReward(REWARD_DTO_1);

        when(service.update(rewardWithID))
                .thenThrow(new RewardNotFoundException(id));

        mockMvc.perform(put(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rewardWithID)))
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        assertTrue(
                                result.getResolvedException() instanceof RewardNotFoundException))
                .andExpect(result ->
                        assertEquals(
                                expectedMessage,
                                Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    @DisplayName("[ 9] given none - when DELETE /opinions - then returns status OK")
    void givenNone_whenDeleteOpinions_thenReturnStatusOk()
            throws Exception {

        final String endpoint = String.format("/%s", DOMAIN);

        mockMvc.perform(delete(endpoint)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[10] given Reward - when DELETE /rewards/:id - then returns status OK")
    void givenReward_whenDeleteRewardsId_thenReturnStatusOk()
            throws Exception {

        final String id = REWARD_1.getId();
        final String endpoint = String.format("/%s/%s", DOMAIN, id);

        mockMvc.perform(delete(endpoint)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[11] given id of not existing reward - when DELETE /rewards/:id - then throw RewardNotFoundException")
    void givenIdOfNotExistingReward_whenDELETERewardsId_thenThrowRewardNotFoundException()
            throws Exception {

        final String id = "hehe";
        final String endpoint = String.format("/%s/%s", DOMAIN, id);
        final String expectedMessage = String.format("Reward with ID = %s not found", id);

        when(service.delete(id))
                .thenThrow(new RewardNotFoundException(id));

        mockMvc.perform(delete(endpoint)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        assertTrue(
                                result.getResolvedException() instanceof RewardNotFoundException))
                .andExpect(result ->
                        assertEquals(
                                expectedMessage,
                                Objects.requireNonNull(result.getResolvedException()).getMessage()));

    }

}
