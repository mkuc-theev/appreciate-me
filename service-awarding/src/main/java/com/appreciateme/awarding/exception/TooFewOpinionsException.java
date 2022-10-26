package com.appreciateme.awarding.exception;

public class TooFewOpinionsException extends RuntimeException {

    public TooFewOpinionsException(int usersAmount, int requiredAmount) {
        super(String.format(
                "User can't claim this reward - too few opinions (having: %d, required: %d)",
                usersAmount, requiredAmount));
    }
}
