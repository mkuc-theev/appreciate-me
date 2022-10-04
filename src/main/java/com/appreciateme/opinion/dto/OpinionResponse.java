package com.appreciateme.opinion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Builder
@AllArgsConstructor
public class OpinionResponse {

    @Id
    private final String id;
    private final String opinionUserID;
    private final String reviewedUserID;
    private String predefinedMessageID;
    private String opinionMessage;
}
