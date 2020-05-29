package com.bridgelabz.onlinebookstore.exceptions;

public class JWTException extends RuntimeException{
    public enum ExceptionType {
        SESSION_TIMEOUT, USER_NOT_FOUND
    }

    public ExceptionType type;

    public JWTException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
