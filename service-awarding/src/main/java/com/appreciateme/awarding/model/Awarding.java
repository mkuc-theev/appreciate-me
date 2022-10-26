package com.appreciateme.awarding.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class Awarding {

    private String userId;
    private List<OwnedReward> rewards;

    public static boolean isCorrect(Awarding awarding) {
        if (awarding == null) {
            return false;
        }

        if (awarding.getUserId() == null || awarding.getUserId().isEmpty()) {
            return false;
        }

        return true;
    }
}

