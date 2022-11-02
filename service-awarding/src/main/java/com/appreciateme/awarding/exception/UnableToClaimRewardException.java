package com.appreciateme.awarding.exception;

import com.appreciateme.reward.model.RewardUtils;

public class UnableToClaimRewardException extends RuntimeException {

    public UnableToClaimRewardException(int userAmount, int requiredAmount) {
        super(String.format(
                "User can't claim this reward - too few opinions (having: %d, required: %d)",
                userAmount, requiredAmount));
    }

    public UnableToClaimRewardException(String dateFrom, String dateTo) {
        super(String.format(
                "User can't claim this reward - it's unavailable due to it's availability time (open from %s to %s - now %s)",
                dateFrom,
                dateTo,
                RewardUtils.getCurrentDate()
        ));
    }
}
