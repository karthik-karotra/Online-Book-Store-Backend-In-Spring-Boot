package com.bridgelabz.onlinebookstore.exceptions;

public class JWTException extends RuntimeException {
    public enum ExceptionType {
        SESSION_TIMEOUT,
        USER_NOT_FOUND
    }

    public JWTException.ExceptionType type;

    public JWTException(String message, JWTException.ExceptionType type) {
        super(message);
        this.type = type;
    }
}
