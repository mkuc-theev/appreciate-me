package com.appreciateme.reward.model;

import java.util.HashMap;
import java.util.Map;

public enum RewardCorrectnessStatus {

    CORRECT("Reward is correct"),
    EMPTY_REWARD("reward is null"),
    EMPTY_ID("Id is empty or null"),
    EMPTY_COMPANY_NAME("CompanyName is empty or null"),
    NEGATIVE_VALUE("value of reward is negative (should be >= 0)"),
    NONPOSITIVE_REQUIRED_OPINION_AMOUNT("requiredOpinionAmount is less than 1 (should be at least 1)"),
    EMPTY_DESCRIPTION("description is empty or null"),
    INCORRECT_DATE_FROM("dateFrom is not null and has incorrect format (should be: yyyy-MM-dd HH:mm:ss)"),
    INCORRECT_DATE_TO("dateTo is not null and has incorrect format (should be: yyyy-MM-dd HH:mm:ss)"),
    FROM_EARLIER_THAN_TO("dateTo is earlier than dateFrom"),
    NONPOSITIVE_AVAILABILITY_DAYS("availabilityDays is less than 1 (should be at least 1)");

    private static final Map<String, RewardCorrectnessStatus> BY_LABEL = new HashMap<>();

    static {
        for (RewardCorrectnessStatus element: values()) {
            BY_LABEL.put(element.label, element);
        }
    }
    public final String label;

    RewardCorrectnessStatus(String label) {
        this.label = label;
    }

    public static RewardCorrectnessStatus ofLabel(String label) {
        return BY_LABEL.get(label);
    }

}
