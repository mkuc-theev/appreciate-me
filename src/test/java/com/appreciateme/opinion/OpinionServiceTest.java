package com.appreciateme.opinion;

import com.appreciateme.opinion.dto.OpinionDTO;
import com.appreciateme.opinion.dto.OpinionMapper;
import com.appreciateme.opinion.model.Opinion;
import com.appreciateme.opinion.repository.OpinionRepository;
import com.appreciateme.opinion.service.OpinionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}
