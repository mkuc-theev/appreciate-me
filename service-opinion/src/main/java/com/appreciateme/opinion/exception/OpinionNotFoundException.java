package com.appreciateme.opinion.exception;

public class OpinionNotFoundException extends RuntimeException {

    /**
     * Exception thrown when Opinion of specified ID don't exist in database
     * @param id    identifier of Opinion
     */
    public OpinionNotFoundException(String id) {
        super(String.format("Opinion with ID = %s not found", id));
    }
}
