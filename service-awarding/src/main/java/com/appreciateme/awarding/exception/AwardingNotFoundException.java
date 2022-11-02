package com.appreciateme.awarding.exception;

public class AwardingNotFoundException extends RuntimeException {

    /**
     * Exception thrown when Reward of specified ID don't exist in database
     * @param id    identifier of Reward
     */
    public AwardingNotFoundException(String id) {
        super(String.format("Reward with ID = %s not found", id));
    }
}
