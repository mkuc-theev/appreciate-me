package com.appreciateme.opinion.model;

import java.util.HashMap;
import java.util.Map;

public enum OpinionCorrectnessStatus {

    CORRECT("opinion is correct"),
    EMPTY_OPINION("reward is null"),
    EMPTY_ID("ID is empty of null"),
    OPINION_USER_ID_EMPTY("opinionUserId is empty (required)"),
    REVIEWED_USER_ID_EMPTY("reviewedUserId is empty (required)"),
    INCORRECT_DATE("date is not null and has incorrect format (should be: yyyy-MM-dd HH:mm:ss)"),
    EMPTY_DATE("date is not null, but it's empty");

    private static final Map<String, OpinionCorrectnessStatus> BY_LABEL = new HashMap<>();

    static {
        for (OpinionCorrectnessStatus element: values()) {
            BY_LABEL.put(element.label, element);
        }
    }

    public final String label;

    OpinionCorrectnessStatus(String label) {
        this.label = label;
    }

    public static OpinionCorrectnessStatus ofLabel(String label) {
        return BY_LABEL.get(label);
    }
}
