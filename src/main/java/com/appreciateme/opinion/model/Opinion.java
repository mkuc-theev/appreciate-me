package com.appreciateme.opinion.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(value = "opinions")
@AllArgsConstructor
@Builder
@Data
public class Opinion {

    @Id
    private final String id;
    @Field("opinionUserID")
    private final String opinionUserID;
    @Field("reviewedUserID")
    private final String reviewedUserID;
    @Field("predefinedMessageID")
    private String predefinedMessageID;
    @Field("opinionMessage")
    private String opinionMessage;

}
