package com.appreciateme.awarding.exception;

public class IncorrectAwardingException extends RuntimeException {

    public IncorrectAwardingException() {
        super("Awarding is incorrect");
    }
}
