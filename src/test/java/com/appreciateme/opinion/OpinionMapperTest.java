package com.appreciateme.opinion;

import com.appreciateme.opinion.dto.OpinionDTO;
import com.appreciateme.opinion.dto.OpinionMapper;
import com.appreciateme.opinion.model.Opinion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OpinionMapperTest {

    final long date = 1665394200L;

    final Opinion OPINION = Opinion.builder()
            .id("633ca00a45ef711325f9d80f")
            .opinionUserID("atyranski")
            .reviewedUserID("pbogdan")
            .predefinedMessageID("goodJob1")
            .opinionMessage("a really nice byczeq")
            .date(LocalDate.ofEpochDay(date))
            .build();

    final List<Opinion> OPINION_LIST = List.of(
            Opinion.builder()
                    .id("633ca00a45ef711325f9d80f")
                    .opinionUserID("atyranski")
                    .reviewedUserID("ykarychkovskyi")
                    .predefinedMessageID("goodJob2")
                    .opinionMessage("very cool mentor")
                    .date(LocalDate.ofEpochDay(date))
                    .build(),
            Opinion.builder()
                    .id("633ca01f45ef711325f9d810")
                    .opinionUserID("atyranski")
                    .reviewedUserID("abaranski")
                    .predefinedMessageID("goodJob3")
                    .opinionMessage("nice bald-beard bro")
                    .date(LocalDate.ofEpochDay(date))
                    .build(),
            Opinion.builder()
                    .id("633d58a19e689e69d12e9e6b")
                    .opinionUserID("atyranski")
                    .reviewedUserID("mkuc")
                    .predefinedMessageID("goodJob4")
                    .opinionMessage("great bass-boosted voice")
                    .date(LocalDate.ofEpochDay(date))
                    .build()
    );

    final OpinionDTO OPINION_DTO = OpinionDTO.builder()
            .id("633ca00a45ef711325f9d80f")
            .opinionUserID("atyranski")
            .reviewedUserID("pbogdan")
            .predefinedMessageID("goodJob1")
            .opinionMessage("a really nice byczeq")
            .date(date)
            .build();

    final List<OpinionDTO> OPINION_DTO_LIST = List.of(
            OpinionDTO.builder()
                    .id("633ca00a45ef711325f9d80f")
                    .opinionUserID("atyranski")
                    .reviewedUserID("ykarychkovskyi")
                    .predefinedMessageID("goodJob2")
                    .opinionMessage("very cool mentor")
                    .date(date)
                    .build(),
            OpinionDTO.builder()
                    .id("633ca01f45ef711325f9d810")
                    .opinionUserID("atyranski")
                    .reviewedUserID("abaranski")
                    .predefinedMessageID("goodJob3")
                    .opinionMessage("nice bald-beard bro")
                    .date(date)
                    .build(),
            OpinionDTO.builder()
                    .id("633d58a19e689e69d12e9e6b")
                    .opinionUserID("atyranski")
                    .reviewedUserID("mkuc")
                    .predefinedMessageID("goodJob4")
                    .opinionMessage("great bass-boosted voice")
                    .date(date)
                    .build()
    );

    @Test
    @DisplayName("[ 1] given OpinionDTO - when toOpinion() - return Opinion")
    void givenOpinionDTO_whenToOpinion_thenReturnOpinion() {
        Opinion opinion = OpinionMapper.toOpinion(OPINION_DTO);

        assertEquals(OPINION, opinion);
    }

    @Test
    @DisplayName("[ 2] given Opinion - when toDto() - return OpinionDTO")
    void givenOpinion_whenToDto_thenReturnOpinionDTO() {
        OpinionDTO opinionDTO = OpinionMapper.toDto(OPINION);

        assertEquals(OPINION_DTO, opinionDTO);
    }

    @Test
    @DisplayName("[ 3] given List<Opinion> - when toDtoList() - return List<OpinionDTO>")
    void givenListOfOpinion_whenToDtoList_thenReturnListOfOpinionDTO() {
        List<OpinionDTO> opinionDTOList = OpinionMapper.toDtoList(OPINION_LIST);

        assertEquals(OPINION_DTO_LIST, opinionDTOList);
    }
}
