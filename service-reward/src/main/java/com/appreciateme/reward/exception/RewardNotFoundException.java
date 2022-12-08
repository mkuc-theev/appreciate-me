package com.appreciateme.reward.exception;

public class RewardNotFoundException extends RuntimeException {

    /**
     * Exception thrown when Reward of specified ID don't exist in database
     * @param id    identifier of Reward
     */
    public RewardNotFoundException(String id) {
        super(String.format("Reward with ID = %s not found", id));
    }
}
