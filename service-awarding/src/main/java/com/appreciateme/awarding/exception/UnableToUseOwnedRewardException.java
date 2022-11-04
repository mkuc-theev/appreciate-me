package com.appreciateme.awarding.exception;

import com.appreciateme.awarding.model.OwnedRewardUtils;
import com.appreciateme.reward.model.RewardUtils;

public class UnableToUseOwnedRewardException extends RuntimeException {

    public UnableToUseOwnedRewardException(long dateFrom, long dateTo) {
        super(String.format(
                "User can't use this reward - it's unavailable due to it's availability time (open from %s to %s - now %s)",
                OwnedRewardUtils.mapLongToStringDate(dateFrom),
                OwnedRewardUtils.mapLongToStringDate(dateTo),
                RewardUtils.getCurrentDate()
        ));
    }

    public UnableToUseOwnedRewardException(String dateFrom, String dateTo) {
        super(String.format(
                "User can't use this reward - it's unavailable due to it's availability time (open from %s to %s - now %s)",
                dateFrom,
                dateTo,
                RewardUtils.getCurrentDate()
        ));
    }
}
