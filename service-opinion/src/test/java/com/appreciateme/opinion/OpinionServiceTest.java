package com.appreciateme.opinion;

import com.appreciateme.opinion.model.OpinionCorrectnessStatus;
import com.appreciateme.opinion.model.OpinionDTO;
import com.appreciateme.opinion.model.OpinionUtils;
import com.appreciateme.opinion.exception.OpinionNotFoundException;
import com.appreciateme.opinion.model.Opinion;
import com.appreciateme.opinion.controller.OpinionRepository;
import com.appreciateme.opinion.controller.OpinionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

public class OpinionServiceTest {

    final String DATE_STRING = "2020-10-10 13:30:10";

    final Opinion OPINION_1 = Opinion.builder()
//            .id("633ca00a45ef711325f9d80f")
            .opinionUserId("atyranski")
            .reviewedUserId("pbogdan")
            .opinionMessage("a really nice byczeq")
            .date(DATE_STRING)
            .build();

    final Opinion OPINION_2 = Opinion.builder()
//            .id("633ca00a45ef711325f9d80f")
            .opinionUserId("atyranski")
            .reviewedUserId("ykarychkovskyi")
            .opinionMessage("very cool mentor")
            .date(DATE_STRING)
            .build();

    final Opinion OPINION_3 = Opinion.builder()
//            .id("633ca01f45ef711325f9d810")
            .opinionUserId("atyranski")
            .reviewedUserId("abaranski")
            .opinionMessage("nice bald-beard bro")
            .date(DATE_STRING)
            .build();

    final OpinionDTO OPINION_DTO_1 = OpinionDTO.builder()
            .id("633ca00a45ef711325f9d80f")
            .opinionUserId(OPINION_1.getOpinionUserId())
            .reviewedUserId(OPINION_1.getReviewedUserId())
            .opinionMessage(OPINION_1.getOpinionMessage())
            .date(OpinionUtils.mapStringDateToLong(OPINION_1.getDate()))
            .build();
    final OpinionDTO OPINION_DTO_2 = OpinionDTO.builder()
            .id("633ca00a45ef711325f9d80f")
            .opinionUserId(OPINION_2.getOpinionUserId())
            .reviewedUserId(OPINION_2.getReviewedUserId())
            .opinionMessage(OPINION_2.getOpinionMessage())
            .date(OpinionUtils.mapStringDateToLong(OPINION_2.getDate()))
            .build();
    final OpinionDTO OPINION_DTO_3 = OpinionDTO.builder()
            .id("633ca01f45ef711325f9d810")
            .opinionUserId(OPINION_3.getOpinionUserId())
            .reviewedUserId(OPINION_3.getReviewedUserId())
            .opinionMessage(OPINION_3.getOpinionMessage())
            .date(OpinionUtils.mapStringDateToLong(OPINION_3.getDate()))
            .build();

    private static final OpinionRepository repository = Mockito.mock(OpinionRepository.class);
    private static final OpinionService service = new OpinionService(repository);

    @Test
    @DisplayName("[ 1] given existing opinions - when getAllOpinions() - then return List<OpinionDTO>")
    public void givenExistingOpinions_whenGetAllOpinions_thenReturnListOpinionsDTO() {
        final List<OpinionDTO> opinionDTOs = List.of(OPINION_DTO_1, OPINION_DTO_2, OPINION_DTO_3);
        final List<Opinion> opinions = OpinionUtils.mapToOpinionList(opinionDTOs);

        given(repository.findAll())
                .willReturn(opinionDTOs);

        final List<Opinion> result = service.getAll();

        then(repository)
                .should()
                .findAll();

        assertEquals(result, opinions);
    }

    @Test
    @DisplayName("[ 2] given existing opinion id - when getOpinionById() - then return OpinionDTO")
    public void givenExistingOpinionId_whenGetOpinionById_thenReturnOpinionDTO() {
        final String id = "633ca00a45ef711325f9d80f";
        final Opinion expectedOpinion = OpinionUtils.mapToOpinion(OPINION_DTO_1);

        given(repository.findById(eq(id)))
                .willReturn(Optional.of(OPINION_DTO_1));

        Opinion result = service.getById(id);

        then(repository)
                .should()
                .findById(eq(id));

        assertEquals(expectedOpinion, result);
    }

    @Test
    @DisplayName("[ 3] given not existing opinion id - when getOpinionById() then throw OpinionNotFoundException")
    public void givenNotExistingOpinionId_whenGetOpinionById_thenThrowOpinionNotFoundException() {
        final String opinionId = "655ca00a45ef711325f9d80f";

        given(repository.findById(eq(opinionId)))
                .willThrow(new OpinionNotFoundException(opinionId));

        assertThrows(
                OpinionNotFoundException.class,
                () -> service.getById(opinionId));
    }

    @Test
    @DisplayName("[ 4] given existing opinions for user - when getOpinionByReviewedUserId() - then return List<OpinionDTO>")
    public void givenExistingOpinionsForUser_whenGetOpinionByReviewedUserId_thenReturnListOpinionDTO() {
        final String reviewedId = "pbogdan";
        final List<Opinion> opinions = List.of(OPINION_1);
        final List<OpinionDTO> opinionsDTO = OpinionUtils.mapToDtoList(opinions);

        given(repository.findAllUnusedByReviewedUserId(eq(reviewedId)))
                .willReturn(opinionsDTO);

        List<Opinion> result = service.getAllUnusedByReviewedUserId(reviewedId);

        then(repository)
                .should()
                .findAllUnusedByReviewedUserId(eq(reviewedId));

        assertEquals(opinions, result);
    }

    @Test
    @DisplayName("[ 5] given String with date in correct format - when isDateFormatCorrect - then return true")
    void givenStringWithDateInCorrectFormat_whenIsDateFormatCorrect_thenReturnTrue() {
        final String date = "2020-10-10 23:59:59";
        final boolean result = Opinion.isDateFormatCorrect(date);

        assertTrue(result);
    }

    @Test
    @DisplayName("[ 6] given String with date in incorrect format - when isDateFormatCorrect - then return false")
    void givenStringWithDateInIncorrectFormat_whenIsDateFormatCorrect_thenReturnFalse() {
        final String date = "2120-20-10 00:60:60";
        boolean result = Opinion.isDateFormatCorrect(date);

        assertFalse(result);
    }

    @Test
    @DisplayName("[ 7] given correct Opinion - when isOpinionCorrect - then return true")
    void givenCorrectOpinion_whenIsOpinionCorrect_thenReturnTrue() {
        final OpinionCorrectnessStatus result = Opinion.isOpinionCorrect(OPINION_1);

        assertEquals(OpinionCorrectnessStatus.CORRECT, result);
    }

    @Test
    @DisplayName("[ 8] given Opinion without rewievedUserId - when isOpinionCorrect - then return true")
    void givenOpinionWithoutRewievedUserId_whenIsOpinionCorrect_thenReturnTrue() {
        final OpinionDTO incorrectOpinionDTO = OpinionDTO.builder()
                .id("1234")
                .opinionUserId("1234")
                .opinionMessage("1234")
                .build();

        final Opinion incorrectOpinion = OpinionUtils.mapToOpinion(incorrectOpinionDTO);
        final OpinionCorrectnessStatus result = Opinion.isOpinionCorrect(incorrectOpinion);

        assertEquals(OpinionCorrectnessStatus.REVIEWED_USER_ID_EMPTY, result);
    }

}
