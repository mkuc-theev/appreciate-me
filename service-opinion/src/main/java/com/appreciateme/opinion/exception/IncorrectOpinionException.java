package com.appreciateme.opinion.exception;

public class IncorrectOpinionException extends RuntimeException {

    public IncorrectOpinionException() {
        super("Provided opinion is incorrect");
    }
}
