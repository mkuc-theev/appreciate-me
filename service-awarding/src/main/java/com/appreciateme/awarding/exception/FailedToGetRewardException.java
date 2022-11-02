package com.appreciateme.awarding.exception;

public class FailedToGetRewardException extends RuntimeException {

    public FailedToGetRewardException(String rewardId) {
        super(String.format("failed to GET a reward from Reward microservice with ID = %s", rewardId));
    }
}
