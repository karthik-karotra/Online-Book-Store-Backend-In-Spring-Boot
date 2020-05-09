package com.bridgelabz.onlinebookstore.exceptions;

public class OnlineBookStoreException extends RuntimeException {
    public enum ExceptionType {BOOK_AND_AUTHOR_NAME_ALREADY_EXISTS,ISBN_NO_ALREADY_EXISTS,NO_BOOK_FOUND}

    public ExceptionType type;

    public OnlineBookStoreException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
