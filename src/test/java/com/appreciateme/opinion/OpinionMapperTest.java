package com.appreciateme.opinion;

import com.appreciateme.opinion.dto.OpinionDTO;
import com.appreciateme.opinion.dto.OpinionMapper;
import com.appreciateme.opinion.model.Opinion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.util.List;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OpinionMapperTest {

    final String DATE_STRING = "2020-10-10 13:30:10";
    final long DATE_TIMESTAMP = 1602329410000L;

    final Opinion OPINION_1 = Opinion.builder()
            .id("633ca00a45ef711325f9d80f")
            .opinionUserID("atyranski")
            .reviewedUserID("pbogdan")
            .predefinedMessageID("goodJob1")
            .opinionMessage("a really nice byczeq")
            .date(DATE_STRING)
            .build();

    final Opinion OPINION_2 = Opinion.builder()
            .id("633ca00a45ef711325f9d80f")
            .opinionUserID("atyranski")
            .reviewedUserID("ykarychkovskyi")
            .predefinedMessageID("goodJob2")
            .opinionMessage("very cool mentor")
            .date(DATE_STRING)
            .build();

    final Opinion OPINION_3 = Opinion.builder()
            .id("633ca01f45ef711325f9d810")
            .opinionUserID("atyranski")
            .reviewedUserID("abaranski")
            .predefinedMessageID("goodJob3")
            .opinionMessage("nice bald-beard bro")
            .date(DATE_STRING)
            .build();

    final OpinionDTO OPINION_DTO_1 = OpinionDTO.builder()
            .id("633ca00a45ef711325f9d80f")
            .opinionUserID("atyranski")
            .reviewedUserID("pbogdan")
            .predefinedMessageID("goodJob1")
            .opinionMessage("a really nice byczeq")
            .date(DATE_TIMESTAMP)
            .build();

    final OpinionDTO OPINION_DTO_2 = OpinionDTO.builder()
            .id("633ca00a45ef711325f9d80f")
            .opinionUserID("atyranski")
            .reviewedUserID("ykarychkovskyi")
            .predefinedMessageID("goodJob2")
            .opinionMessage("very cool mentor")
            .date(DATE_TIMESTAMP)
            .build();

    final OpinionDTO OPINION_DTO_3 = OpinionDTO.builder()
            .id("633ca01f45ef711325f9d810")
            .opinionUserID("atyranski")
            .reviewedUserID("abaranski")
            .predefinedMessageID("goodJob3")
            .opinionMessage("nice bald-beard bro")
            .date(DATE_TIMESTAMP)
            .build();


    @Test
    @DisplayName("[ 1] given OpinionDTO - when toOpinion() - return Opinion")
    void givenOpinionDTO_whenToOpinion_thenReturnOpinion() {
        Opinion opinion = OpinionMapper.toOpinion(OPINION_DTO_1);

        assertEquals(OPINION_1, opinion);
    }

    @Test
    @DisplayName("[ 2] given Opinion - when toDto() - return OpinionDTO")
    void givenOpinion_whenToDto_thenReturnOpinionDTO() {
        OpinionDTO opinionDTO = OpinionMapper.toDto(OPINION_1);

        assertEquals(OPINION_DTO_1, opinionDTO);
    }

    @Test
    @DisplayName("[ 3] given List<Opinion> - when toDtoList() - return List<OpinionDTO>")
    void givenListOfOpinion_whenToDtoList_thenReturnListOfOpinionDTO() {
        List<Opinion> opinions = List.of(OPINION_1, OPINION_2, OPINION_3);
        List<OpinionDTO> opinionDTOs = List.of(OPINION_DTO_1, OPINION_DTO_2, OPINION_DTO_3);

        List<OpinionDTO> opinionDTOList = OpinionMapper.toDtoList(opinions);

        assertEquals(opinionDTOs, opinionDTOList);
    }

    @Test
    @DisplayName("[ 4] given long timestamp - when mapLongToStringDate() - return date as String")
    void givenLongTimestamp_whenMapLongToStringDate_thenReturnDateAsString() {
        String result = OpinionMapper.mapLongToStringDate(DATE_TIMESTAMP);

        assertEquals(DATE_STRING, result);
    }

    @Test
    @DisplayName("[ 5] given string date - when mapStringDateToLong() - return date as timestamp")
    void givenStringDate_whenMapStringDateToLong_thenReturnDateAsTimestamp() {
        long result = OpinionMapper.mapStringDateToLong(DATE_STRING);

        assertEquals(DATE_TIMESTAMP, result);
    }
}
