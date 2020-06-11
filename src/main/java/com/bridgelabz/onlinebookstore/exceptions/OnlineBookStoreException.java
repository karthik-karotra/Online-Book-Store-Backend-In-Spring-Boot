package com.bridgelabz.onlinebookstore.exceptions;

public class OnlineBookStoreException extends RuntimeException {
    public enum ExceptionType {
        NO_BOOK_FOUND,
        FILE_NOT_FOUND,
        INVALID_DATA,
    }

    public ExceptionType type;

    public OnlineBookStoreException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
