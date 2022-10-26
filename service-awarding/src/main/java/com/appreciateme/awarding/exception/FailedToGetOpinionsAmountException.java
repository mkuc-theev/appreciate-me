package com.appreciateme.awarding.exception;

public class FailedToGetOpinionsAmountException extends RuntimeException {

    public FailedToGetOpinionsAmountException() {
        super("Awarding Microservice couldn't get a response from Opinion Microservice");
    }
}
