package com.appreciateme.opinion.dto;

import com.appreciateme.opinion.model.Opinion;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OpinionMapper {

    /**
     * Method mapping List of Opinions into List of OpinionDTO's
     * @param opinions  List of Opinions
     * @return          List of OpinionDTO's
     */
    public static List<OpinionDTO> toDtoList(List<Opinion> opinions) {
        return opinions.stream()
                .map(OpinionMapper::toDto)
                .toList();
    }

    /**
     * Method mapping specified Opinion object into OpinionDTO object
     * @param opinion   Opinion object
     * @return          OpinionDTO object
     */
    public static OpinionDTO toDto(Opinion opinion) {
        return OpinionDTO.builder()
                .id(opinion.getId())
                .opinionUserID(opinion.getOpinionUserID())
                .reviewedUserID(opinion.getReviewedUserID())
                .predefinedMessageID(opinion.getPredefinedMessageID())
                .opinionMessage(opinion.getOpinionMessage())
                .build();
    }

    /**
     * Method mapping specified OpinionDTO object into Opinion object
     * @param opinionDTO    OpinionDTO object
     * @return              Opinion object
     */
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
