package com.appreciateme.opinion.dto;

import com.appreciateme.opinion.model.Opinion;
import org.springframework.stereotype.Component;

@Component
public class OpinionMapper {

    public static OpinionDTO toDto(Opinion opinion) {
        return OpinionDTO.builder()
                .id(opinion.getId())
                .opinionUserID(opinion.getOpinionUserID())
                .reviewedUserID(opinion.getReviewedUserID())
                .predefinedMessageID(opinion.getPredefinedMessageID())
                .opinionMessage(opinion.getOpinionMessage())
                .build();
    }

    public static Opinion toOpinion(OpinionDTO opinionDTO) {
        return Opinion.builder()
                .id(opinionDTO.getId())
                .opinionUserID(opinionDTO.getOpinionUserID())
                .reviewedUserID(opinionDTO.getReviewedUserID())
                .predefinedMessageID(opinionDTO.getPredefinedMessageID())
                .opinionMessage(opinionDTO.getOpinionMessage())
                .build();
    }

}
