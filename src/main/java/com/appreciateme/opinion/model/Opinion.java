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

    /**
     * Auto-generated identifier of opinion
     */
    @Id
    private final String id;

    /**
     * ID of user which made specific opinion
     */
    @Field("opinionUser")
    private final String opinionUserID;

    /**
     * ID of user which has been reviewed by
     * user with ID specified in 'opinionUserId
     */
    @Field("reviewedUser")
    private final String reviewedUserID;

    /**
     * ID of one from the predefined opinion messages
     */
    @Field("predefinedMessage")
    private String predefinedMessageID;

    /**
     * Custom opinion message made by opinionUser
     */
    @Field("opinionMessage")
    private String opinionMessage;

}
