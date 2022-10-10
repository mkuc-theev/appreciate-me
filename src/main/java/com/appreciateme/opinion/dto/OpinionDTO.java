package com.appreciateme.opinion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OpinionDTO {

    private String id;
    private String opinionUserID;
    private String reviewedUserID;
    private String predefinedMessageID;
    private String opinionMessage;
    private Long date;
}
