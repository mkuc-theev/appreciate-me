package com.appreciateme.opinion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OpinionRequest {

    private final String opinionUserID;
    private final String reviewedUserID;
    private String predefinedMessageID;
    private String opinionMessage;

}
