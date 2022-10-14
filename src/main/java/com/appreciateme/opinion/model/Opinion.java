package com.appreciateme.opinion.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
@Builder
@Data
public class Opinion {

    private final static String DATE_REGEX =
            "^(19|20)[0-9][0-9]-(0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-9]|3[0-1]) (0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$";

    /**
     * Auto-generated identifier of opinion
     */
    @Field("id")
    private String id;

    /**
     * ID of user which made specific opinion
     */
    @Field("opinionUser")
    private String opinionUserID;

    /**
     * ID of user which has been reviewed by
     * user with ID specified in 'opinionUserId
     */
    @Field("reviewedUser")
    private String reviewedUserID;

    /**
     * Date of creating particular opinion
     */
    @Field("date")
    private String date;

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

    /**
     * Verify if provided String represents date in correct format
     * @param date      String with date to check
     * @return          true if date is in correct format, false if not
     */
    public static boolean isDateFormatCorrect(String date) {
        Pattern pattern = Pattern.compile(DATE_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(date);
        return matcher.find();
    }

    /**
     * Verify provided Opinion is not a null, has id, opinionUserId, ReviewedUserId and date
     * @param opinion   opinion object to check
     * @return          true if opinion is correct, false if not
     */
    public static boolean isOpinionCorrect(Opinion opinion) {
        if (opinion == null) {
            return false;
        }

        if (opinion.getId() != null && opinion.getId().isEmpty()) {
            return false;
        }

        if (opinion.getOpinionUserID() == null || opinion.getOpinionUserID().isEmpty()) {
            return false;
        }

        if (opinion.getReviewedUserID() == null || opinion.getReviewedUserID().isEmpty()) {
            return false;
        }

        if (opinion.getDate() != null && opinion.getDate().isEmpty()) {
            return false;
        }

        if (opinion.getDate() != null && !opinion.getDate().isEmpty()) {
            return isDateFormatCorrect(opinion.getDate());
        }

        return true;
    }
}
