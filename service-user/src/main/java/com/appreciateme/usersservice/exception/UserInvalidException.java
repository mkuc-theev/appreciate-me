package com.appreciateme.usersservice.exception;


public class UserInvalidException extends RuntimeException {

  public UserInvalidException() {
    super("Provided user is invalid!");
  }
}
