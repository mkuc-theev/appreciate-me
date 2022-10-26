package com.appreciateme.reward.exception;

import com.appreciateme.reward.model.RewardCorrectnessStatus;

public class IncorrectRewardException extends RuntimeException {

    public IncorrectRewardException() {
        super("Provided Reward is incorrect");
    }

    public IncorrectRewardException(RewardCorrectnessStatus status) {
        super(String.format("Incorrect Reward: %s", status.label));
    }
}
