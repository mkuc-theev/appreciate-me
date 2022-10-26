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

    private String id;
    private String companyName;
    private boolean isService;
    private double value;
    private int requiredOpinionAmount;
    private String description;
    private String dateFrom;
    private String dateTo;
    private long availabilityDays;

    public static boolean isDateFormatCorrect(String date) {
        Pattern pattern = Pattern.compile(DATE_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(date);
        return matcher.find();
    }

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
