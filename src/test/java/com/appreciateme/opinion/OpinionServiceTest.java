package com.appreciateme.opinion;

import com.appreciateme.opinion.dto.OpinionDTO;
import com.appreciateme.opinion.dto.OpinionMapper;
import com.appreciateme.opinion.exception.OpinionNotFoundException;
import com.appreciateme.opinion.model.Opinion;
import com.appreciateme.opinion.repository.OpinionRepository;
import com.appreciateme.opinion.service.OpinionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

public class OpinionServiceTest {

    final Opinion OPINION = Opinion.builder()
            .id("633ca00a45ef711325f9d80f")
            .opinionUserID("atyranski")
            .reviewedUserID("pbogdan")
            .predefinedMessageID("goodJob1")
            .opinionMessage("a really nice byczeq")
            .build();

    final List<Opinion> OPINION_LIST = List.of(
            Opinion.builder()
                    .id("633ca00a45ef711325f9d80f")
                    .opinionUserID("atyranski")
                    .reviewedUserID("ykarychkovskyi")
                    .predefinedMessageID("goodJob2")
                    .opinionMessage("very cool mentor")
                    .build(),
            Opinion.builder()
                    .id("633ca01f45ef711325f9d810")
                    .opinionUserID("atyranski")
                    .reviewedUserID("abaranski")
                    .predefinedMessageID("goodJob3")
                    .opinionMessage("nice bald-beard bro")
                    .build(),
            Opinion.builder()
                    .id("633d58a19e689e69d12e9e6b")
                    .opinionUserID("atyranski")
                    .reviewedUserID("mkuc")
                    .predefinedMessageID("goodJob4")
                    .opinionMessage("great bass-boosted voice")
                    .build()
    );

    final OpinionDTO OPINION_DTO = OpinionMapper.toDto(OPINION);
    final List<OpinionDTO> OPINION_DTO_LIST = OpinionMapper.toDtoList(OPINION_LIST);

    private static final OpinionRepository repository = Mockito.mock(OpinionRepository.class);
    private static final OpinionService service = new OpinionService(repository);

    @Test
    @DisplayName("[ 1] given existing opinions - when getAllOpinions() - then return List<OpinionDTO>")
    public void givenExistingOpinions_whenGetAllOpinions_thenReturnListOpinionsDTO() {
        given(repository.findAll())
                .willReturn(OPINION_LIST);

        List<OpinionDTO> result = service.getAllOpinions();

        then(repository)
                .should()
                .findAll();

        assertEquals(result, OPINION_DTO_LIST);
    }

    @Test
    @DisplayName("[ 2] given existing opinion id - when getOpinionById() - then return OpinionDTO")
    public void givenExistingOpinionId_whenGetOpinionById_thenReturnOpinionDTO() {
        String id = "633ca00a45ef711325f9d80f";

        given(repository.findById(eq(id)))
                .willReturn(Optional.of(OPINION));

        OpinionDTO result = service.getOpinionById(id);

        then(repository)
                .should()
                .findById(eq(id));

        assertEquals(OPINION_DTO, result);
    }

    @Test
    @DisplayName("[ 3] given not existing opinion id - when getOpinionById() then throw OpinionNotFoundException")
    public void givenNotExistingOpinionId_whenGetOpinionById_thenThrowOpinionNotFoundException() {
        String opinionId = "655ca00a45ef711325f9d80f";

        given(repository.findById(eq(opinionId)))
                .willThrow(new OpinionNotFoundException(opinionId));

        assertThrows(
                OpinionNotFoundException.class,
                () -> service.getOpinionById(opinionId));
    }

    @Test
    @DisplayName("[ 4] given existing opinions of user - when getOpinionByOpinionUserId() - then return List<OpinionDTO>")
    public void givenExistingOpinionsOfUser_whenGetOpinionByOpinionUserId_thenReturnListOpinionDTO() {
        String opinionUserId = "atyranski";

        given(repository.findByOpinionUserId(eq(opinionUserId)))
                .willReturn(OPINION_LIST);

        List<OpinionDTO> result = service.getOpinionByOpinionUserId(opinionUserId);

        then(repository)
                .should()
                .findByOpinionUserId(eq(opinionUserId));

        assertEquals(OPINION_DTO_LIST, result);
    }

    @Test
    @DisplayName("[ 5] given existing opinions for user - when getOpinionByReviewedUserId() - then return List<OpinionDTO>")
    public void givenExistingOpinionsForUser_whenGetOpinionByReviewedUserId_thenReturnListOpinionDTO() {
        String reviewedId = "pbogdan";
        List<Opinion> opinions = List.of(OPINION);
        List<OpinionDTO> opinionsDTO = OpinionMapper.toDtoList(opinions);

        given(repository.findByReviewedUserId(eq(reviewedId)))
                .willReturn(opinions);

        List<OpinionDTO> result = service.getOpinionByReviewedUserId(reviewedId);

        then(repository)
                .should()
                .findByReviewedUserId(eq(reviewedId));

        assertEquals(opinionsDTO, result);
    }

}
