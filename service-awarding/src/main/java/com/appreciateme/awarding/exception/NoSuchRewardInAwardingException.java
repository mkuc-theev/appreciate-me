package com.appreciateme.awarding.exception;

public class NoSuchRewardInAwardingException extends RuntimeException {

    public NoSuchRewardInAwardingException(String userId, String rewardId) {
        super(String.format("There is no reward with id=%s for user with id=%s", userId, rewardId));
    }
}
