package com.appreciateme.opinion;

import com.appreciateme.opinion.controller.OpinionController;
import com.appreciateme.opinion.model.OpinionDTO;
import com.appreciateme.opinion.model.OpinionUtils;
import com.appreciateme.opinion.exception.IncorrectOpinionException;
import com.appreciateme.opinion.exception.OpinionNotFoundException;
import com.appreciateme.opinion.model.Opinion;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@WebMvcTest(OpinionController.class)
public class OpinionControllerTest {

    final String DATE = "2020-10-10 13:30:10";
    final String DOMAIN = "opinions";

    final Opinion OPINION_1 = Opinion.builder()
            .id("633ca00a45ef711325f9d80f")
            .opinionUserId("atyranski")
            .reviewedUserID("pbogdan")
            .opinionMessage("a really nice byczeq")
            .date(DATE)
            .build();

    final Opinion OPINION_2 = Opinion.builder()
            .id("633ca00a45ef711325f9d80f")
            .opinionUserId("atyranski")
            .reviewedUserID("ykarychkovskyi")
            .opinionMessage("very cool mentor")
            .date(DATE)
            .build();

    final Opinion OPINION_3 = Opinion.builder()
            .id("633ca01f45ef711325f9d810")
            .opinionUserID("atyranski")
            .reviewedUserID("abaranski")
            .opinionMessage("nice bald-beard bro")
            .date(DATE)
            .build();

    final Opinion OPINION_4 = Opinion.builder()
            .id("633d58a19e689e69d12e9e6b")
            .opinionUserID("atyranski")
            .reviewedUserID("mkuc")
            .date(DATE)
            .build();

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    OpinionService service;

    @Test
    @DisplayName("[ 1] given List<Opinion> - when GET /opinions - then return status OK")
    void givenOpinionList_whenGetOpinions_thenReturnStatusOk()
            throws Exception {

        final List<Opinion> records = List.of(OPINION_1, OPINION_2, OPINION_3, OPINION_4);
        final String endpoint = String.format("/%s", DOMAIN);

        when(service.getAll())
                .thenReturn(records);

        mockMvc.perform(get(endpoint)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].id").value(OPINION_1.getId()))
                .andExpect(jsonPath("$[0].opinionUserID").value(OPINION_1.getOpinionUserID()))
                .andExpect(jsonPath("$[0].reviewedUserID").value(OPINION_1.getReviewedUserID()))
                .andExpect(jsonPath("$[0].opinionMessage").value(OPINION_1.getOpinionMessage()))
                .andExpect(jsonPath("$[0].date").value(OPINION_1.getDate()))
                .andExpect(jsonPath("$[1].id").value(OPINION_2.getId()))
                .andExpect(jsonPath("$[2].id").value(OPINION_3.getId()))
                .andExpect(jsonPath("$[3].id").value(OPINION_4.getId()));
    }

    @Test
    @DisplayName("[ 2] given Opinion - when GET /opinions/:id - then returns status OK")
    void givenOpinion_whenGetOpinionsId_thenReturnStatusOk()
            throws Exception {

        final String id = OPINION_1.getId();
        final String endpoint = String.format("/%s/%s", DOMAIN, id);

        when(service.getById(id))
                .thenReturn(OPINION_1);

        mockMvc.perform(get(endpoint)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id").value(OPINION_1.getId()))
                .andExpect(jsonPath("$.opinionUserID").value(OPINION_1.getOpinionUserID()))
                .andExpect(jsonPath("$.reviewedUserID").value(OPINION_1.getReviewedUserID()))
                .andExpect(jsonPath("$.opinionMessage").value(OPINION_1.getOpinionMessage()))
                .andExpect(jsonPath("$.date").value(OPINION_1.getDate()));
    }

    @Test
    @DisplayName("[ 3] given not existing opinion id - when GET /opinions/:id - then throw OpinionNotFoundException")
    void givenNotExistingOpinionId_whenGetOpinionsId_thenThrowOpinionNotFoundException()
            throws Exception {

        final String id = "abcd";
        final String endpoint = String.format("/%s/%s", DOMAIN, id);
        final String expectedMessage = String.format("Opinion with ID = %s not found", id);

        when(service.getById(id))
                .thenThrow(new OpinionNotFoundException(id));

        mockMvc.perform(get(endpoint)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        assertTrue(
                                result.getResolvedException() instanceof OpinionNotFoundException))
                .andExpect(result ->
                        assertEquals(
                                expectedMessage,
                                Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    @DisplayName("[ 4] given List<Opinion> - when GET /opinions/opinionUser/:id - then returns status OK")
    void givenOpinion_whenGetOpinionsOpinionUserId_thenReturnStatusOk()
            throws Exception {

        final String id = OPINION_1.getId();
        final List<Opinion> opinions = List.of(OPINION_1, OPINION_2, OPINION_3, OPINION_4);
        final String endpoint = String.format("/%s/opinionUser/%s", DOMAIN, id);

        when(service.getByOpinionUserId(id))
                .thenReturn(opinions);

        mockMvc.perform(get(endpoint)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].id").value(OPINION_1.getId()))
                .andExpect(jsonPath("$[0].opinionUserID").value(OPINION_1.getOpinionUserID()))
                .andExpect(jsonPath("$[0].reviewedUserID").value(OPINION_1.getReviewedUserID()))
                .andExpect(jsonPath("$[0].opinionMessage").value(OPINION_1.getOpinionMessage()))
                .andExpect(jsonPath("$[0].date").value(OPINION_1.getDate()))
                .andExpect(jsonPath("$[1].id").value(OPINION_2.getId()))
                .andExpect(jsonPath("$[2].id").value(OPINION_3.getId()))
                .andExpect(jsonPath("$[3].id").value(OPINION_4.getId()));
    }

    @Test
    @DisplayName("[ 5] given List<Opinion> - when GET /opinions/reviewedUser/:id - then returns status OK")
    void givenOpinion_whenGetOpinionsReviewedUserId_thenReturnStatusOk()
            throws Exception {

        final String id = OPINION_3.getReviewedUserID();
        final List<Opinion> opinions = List.of(OPINION_3);
        final String endpoint = String.format("/%s/reviewedUser/%s", DOMAIN, id);

        when(service.getByReviewedUserId(id))
                .thenReturn(opinions);

        mockMvc.perform(get(endpoint)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(OPINION_3.getId()))
                .andExpect(jsonPath("$[0].opinionUserID").value(OPINION_3.getOpinionUserID()))
                .andExpect(jsonPath("$[0].reviewedUserID").value(OPINION_3.getReviewedUserID()))
                .andExpect(jsonPath("$[0].opinionMessage").value(OPINION_3.getOpinionMessage()))
                .andExpect(jsonPath("$[0].date").value(OPINION_3.getDate()));
    }

    @Test
    @DisplayName("[ 6] given List<Opinion> - when GET /opinions/date/:date - then returns status OK")
    void givenOpinion_whenGetOpinionsDate_thenReturnStatusOk()
            throws Exception {

        final List<Opinion> opinions = List.of(OPINION_1, OPINION_2, OPINION_3, OPINION_4);
        final String endpoint = String.format("/%s/date/%s", DOMAIN, DATE);

        when(service.getByDate(DATE))
                .thenReturn(opinions);

        mockMvc.perform(get(endpoint)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].id").value(OPINION_1.getId()))
                .andExpect(jsonPath("$[0].opinionUserID").value(OPINION_1.getOpinionUserID()))
                .andExpect(jsonPath("$[0].reviewedUserID").value(OPINION_1.getReviewedUserID()))
                .andExpect(jsonPath("$[0].opinionMessage").value(OPINION_1.getOpinionMessage()))
                .andExpect(jsonPath("$[0].date").value(OPINION_1.getDate()))
                .andExpect(jsonPath("$[1].id").value(OPINION_2.getId()))
                .andExpect(jsonPath("$[2].id").value(OPINION_3.getId()))
                .andExpect(jsonPath("$[3].id").value(OPINION_4.getId()));
    }

    @Test
    @DisplayName("[ 7] given Opinion - when POST /opinions - then returns status CREATED")
    void givenOpinion_whenPostOpinions_thenReturnStatusCreated()
            throws Exception {

        final String endpoint = String.format("/%s", DOMAIN);
        final Opinion opinionToCreate = Opinion.builder()
                .opinionUserID(OPINION_1.getOpinionUserID())
                .reviewedUserID(OPINION_1.getReviewedUserID())
                .opinionMessage(OPINION_1.getOpinionMessage())
                .build();

        when(service.add(opinionToCreate))
                .thenReturn(OPINION_1.getId());

        mockMvc.perform(post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(opinionToCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$").value(OPINION_1.getId()));
    }

    @Test
    @DisplayName("[ 8] given incorrect opinion - when POST /opinions - then throw IncorrectOpinionException")
    void givenIncorrectOpinion_whenPostOpinions_thenThrowIncorrectOpinionException()
            throws Exception {

        final OpinionDTO incorrectOpinionDTO = OpinionDTO.builder()
                .id("1234")
                .opinionUserID("1234")
                .opinionMessage("1234")
                .build();

        final Opinion incorrectOpinion = OpinionUtils.mapToOpinion(incorrectOpinionDTO);
        final String endpoint = String.format("/%s", DOMAIN);
        final String expectedMessage = "Provided opinion is incorrect";

        when(service.add(incorrectOpinion))
                .thenThrow(new IncorrectOpinionException());

        mockMvc.perform(post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incorrectOpinion)))
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertTrue(
                                result.getResolvedException() instanceof IncorrectOpinionException))
                .andExpect(result ->
                        assertEquals(
                                expectedMessage,
                                Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    @DisplayName("[ 9] given Opinion - when PUT /opinions - then returns status OK")
    void givenOpinion_whenPutOpinions_thenReturnStatusOk()
            throws Exception {

        final String endpoint = String.format("/%s", DOMAIN);

        when(service.update(OPINION_1))
                .thenReturn(OPINION_1);

        mockMvc.perform(put(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(OPINION_1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id").value(OPINION_1.getId()))
                .andExpect(jsonPath("$.opinionUserID").value(OPINION_1.getOpinionUserID()))
                .andExpect(jsonPath("$.reviewedUserID").value(OPINION_1.getReviewedUserID()))
                .andExpect(jsonPath("$.opinionMessage").value(OPINION_1.getOpinionMessage()))
                .andExpect(jsonPath("$.date").value(OPINION_1.getDate()));
    }

    @Test
    @DisplayName("[ 10] given opinion with not existing id - when PUT /opinions - then throw OpinionNotFoundException")
    void givenOpinionWithNotExistingId_whenPutOpinions_thenThrowOpinionNotFoundException()
            throws Exception {

        final String id = "abcd";
        final String endpoint = String.format("/%s", DOMAIN);
        final String expectedMessage = String.format("Opinion with ID = %s not found", id);

        OpinionDTO opinionDTO = OpinionUtils.mapToDto(OPINION_1);
        opinionDTO.setId(id);
        Opinion opinion = OpinionUtils.mapToOpinion(opinionDTO);

        when(service.update(opinion))
                .thenThrow(new OpinionNotFoundException(id));

        mockMvc.perform(put(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(opinion)))
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        assertTrue(
                                result.getResolvedException() instanceof OpinionNotFoundException))
                .andExpect(result ->
                        assertEquals(
                                expectedMessage,
                                Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    @DisplayName("[11] given none - when DELETE /opinions - then returns status OK")
    void givenNone_whenDeleteOpinions_thenReturnStatusOk()
            throws Exception {

        final String endpoint = String.format("/%s", DOMAIN);

        mockMvc.perform(delete(endpoint)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[12] given Opinion - when DELETE /opinions/:id - then returns status OK")
    void givenOpinion_whenDeleteOpinionsId_thenReturnStatusOk()
            throws Exception {

        final String id = OPINION_1.getId();
        final String endpoint = String.format("/%s/%s", DOMAIN, id);

        mockMvc.perform(delete(endpoint)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[13] given not existing opinion id - when DELETE /opinions/:id - then throw OpinionNotFoundException")
    void givenNotExistingOpinionId_whenDeleteOpinionsId_thenThrowOpinionNotFoundException()
            throws Exception {

        final String id = "abcd";
        final String endpoint = String.format("/%s/%s", DOMAIN, id);
        final String expectedMessage = String.format("Opinion with ID = %s not found", id);

        when(service.delete(id))
                .thenThrow(new OpinionNotFoundException(id));

        mockMvc.perform(delete(endpoint)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        assertTrue(
                                result.getResolvedException() instanceof OpinionNotFoundException))
                .andExpect(result ->
                        assertEquals(
                                expectedMessage,
                                Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }
}
