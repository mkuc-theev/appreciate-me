package com.appreciateme.awarding.exception;

import com.appreciateme.reward.model.RewardUtils;

public class UnableToUseRewardException extends RuntimeException {

    public UnableToUseRewardException(String dateFrom, String dateTo) {
        super(String.format(
                "User can't use this reward - it's unavailable due to it's availability time (open from %s to %s - now %s)",
                dateFrom,
                dateTo,
                RewardUtils.getCurrentDate()
        ));
    }
}
