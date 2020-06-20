package com.bridgelabz.onlinebookstore.exceptions;

public class UserException extends RuntimeException {
    public enum ExceptionType {
        USER_ALREADY_EXISTS, EMAIL_NOT_FOUND, PASSWORD_INVALID, ALREADY_VERIFIED, INVALID_DATA

    }

    public UserException.ExceptionType type;

    public UserException(String message, UserException.ExceptionType type) {
        super(message);
        this.type = type;
    }

}
