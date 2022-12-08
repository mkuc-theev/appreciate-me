package com.appreciateme.reward.exception;

public class FailedToGetRewardsException extends RuntimeException {

    public FailedToGetRewardsException() {
        super("Reward Microservice couldn't get a response from Opinion Microservice");
    }
}
