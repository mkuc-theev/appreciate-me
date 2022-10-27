package com.appreciateme.reward.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class Reward {

    private final static String DATE_REGEX =
            "^(19|20)[0-9][0-9]-(0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-9]|3[0-1]) (0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$";

    /**
     * Auto-generated identifier of reward
     */
    private String id;

    /**
     * Name of company that is responsible for this reward
     */
    private String companyName;

    /**
     * Flag that inform user if this reward is a service or an item
     */
    private boolean isService;

    /**
     * Value of discount that user gets
     */
    private double value;

    /**
     * Required amount of unused opinions that user needs, to claim this reward
     */
    private int requiredOpinionAmount;

    /**
     * Short description of reward details
     */
    private String description;

    /**
     * Date when reward start to be claimable by users
     */
    private String dateFrom;

    /**
     * Date when reward won't be available for users to claim anymore
     */
    private String dateTo;

    /**
     * Number of days, when reward will be available for user to use, when he claims it
     */
    private long availabilityDays;


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
     * Verify correctness provided Reward
     * @param reward    reward object to check
     * @return          RewardCorrectnessStatus value according to state
     */
    public static RewardCorrectnessStatus isCorrect(Reward reward) {
        if (reward == null) {
            return RewardCorrectnessStatus.EMPTY_REWARD;
        }

        if (reward.getId() != null && reward.getId().isEmpty()) {
            return RewardCorrectnessStatus.EMPTY_ID;
        }

        if (reward.getCompanyName() == null || reward.getCompanyName().isEmpty()) {
            return RewardCorrectnessStatus.EMPTY_COMPANY_NAME;
        }

        if (reward.getValue() < 0) {
            return RewardCorrectnessStatus.NEGATIVE_VALUE;
        }

        if (reward.requiredOpinionAmount < 1) {
            return RewardCorrectnessStatus.NONPOSITIVE_REQUIRED_OPINION_AMOUNT;
        }

        if (reward.getDescription() == null || reward.getDescription().isEmpty()) {
            return RewardCorrectnessStatus.EMPTY_DESCRIPTION;
        }

        if (reward.getDateFrom() != null && !isDateFormatCorrect(reward.getDateFrom())) {
            return RewardCorrectnessStatus.INCORRECT_DATE_FROM;
        }

        if (reward.getDateTo() != null && !isDateFormatCorrect(reward.getDateTo())) {
            return RewardCorrectnessStatus.INCORRECT_DATE_TO;
        }

        final long dateFrom = RewardUtils.mapStringDateToLong(reward.getDateFrom());
        final long dateTo = RewardUtils.mapStringDateToLong(reward.getDateTo());

        if (reward.getDateFrom() != null && reward.getDateTo() != null && dateFrom > dateTo) {
            return RewardCorrectnessStatus.FROM_EARLIER_THAN_TO;
        }

        if (reward.availabilityDays < 1) {
            return RewardCorrectnessStatus.NONPOSITIVE_AVAILABILITY_DAYS;
        }

        return RewardCorrectnessStatus.CORRECT;
    }
}
