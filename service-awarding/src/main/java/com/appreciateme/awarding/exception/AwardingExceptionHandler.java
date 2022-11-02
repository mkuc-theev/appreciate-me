package com.appreciateme.awarding.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AwardingExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 400 - The server cannot or will not process the request due to an apparent client error
     * (e.g., malformed request syntax, size too large, invalid request message framing
     * or deceptive request routing)
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = { IncorrectAwardingException.class })
    protected ResponseEntity<Object> handleBadRequest(final RuntimeException ex, final WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    /**
     * 403 - The request contained valid data and was understood by the server, but the server is refusing action.
     * This may be due to the user not having the necessary permissions for a resource or needing an account of some sort,
     * or attempting a prohibited action
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = { UnableToClaimRewardException.class })
    protected ResponseEntity<Object> handleForbidden(final RuntimeException ex, final WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    /**
     * 404 - The requested resource could not be found but may be available in the future.
     * Subsequent requests by the client are permissible.
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = { AwardingNotFoundException.class, NoSuchRewardInAwardingException.class })
    protected ResponseEntity<Object> handleNotFound(final RuntimeException ex, final WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
