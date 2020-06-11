package com.bridgelabz.onlinebookstore.exceptions;

public class AdminException extends RuntimeException {
    public enum ExceptionType {
        ISBN_NO_ALREADY_EXISTS,
        BOOK_AND_AUTHOR_NAME_ALREADY_EXISTS,
        INCOMPATIBLE_TYPE,
        INVALID_DATA

    }

    public AdminException.ExceptionType type;

    public AdminException(String message, AdminException.ExceptionType type) {
        super(message);
        this.type = type;
    }
}
