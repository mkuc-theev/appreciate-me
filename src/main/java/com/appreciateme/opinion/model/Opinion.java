package com.appreciateme.opinion.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@Builder
@Data
public class Opinion {

    /**
     * Auto-generated identifier of opinion
     */
    @Field("id")
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
     * Date of creating particular opinion
     */
    @Field("date")
    private final String date;

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
