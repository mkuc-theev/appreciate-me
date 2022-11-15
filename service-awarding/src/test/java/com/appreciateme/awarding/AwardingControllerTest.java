package com.appreciateme.awarding;

import com.appreciateme.awarding.controller.AwardingController;
import com.appreciateme.awarding.controller.AwardingService;
import com.appreciateme.awarding.exception.AwardingNotFoundException;
import com.appreciateme.awarding.model.Awarding;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.appreciateme.awarding.TestObjects.AWARDING_1;
import static com.appreciateme.awarding.TestObjects.AWARDING_2;
import static com.appreciateme.awarding.TestObjects.AWARDING_DTO_1;

import static java.util.Objects.requireNonNull;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@WebMvcTest(AwardingController.class)
public class AwardingControllerTest {

    final String DOMAIN = "awardings";

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
                                requireNonNull(result.getResolvedException()).getMessage()));
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
                                requireNonNull(result.getResolvedException()).getMessage()));
    }

}
