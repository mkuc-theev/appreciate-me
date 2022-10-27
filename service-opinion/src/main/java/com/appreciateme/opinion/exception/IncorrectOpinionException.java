package com.appreciateme.opinion.exception;

import com.appreciateme.opinion.model.OpinionCorrectnessStatus;

public class IncorrectOpinionException extends RuntimeException {

    public IncorrectOpinionException() {
        super("Provided opinion is incorrect");
    }

    public IncorrectOpinionException(OpinionCorrectnessStatus status) {
        super(String.format("Incorrect Opinion: %s", status.label));
    }
}
