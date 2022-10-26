package com.appreciateme.opinion.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "opinions")
@Data
@Builder
@AllArgsConstructor
public class OpinionDTO {

    @Id
    private String id;
    private String opinionUserId;
    private String reviewedUserId;
    private long date;
    private String opinionMessage;
    private boolean used;
}
