package com.bridgelabz.onlinebookstore.exceptions;

public class CartException extends RuntimeException {

    public enum ExceptionType {
        ORDER_QUANTITY_GREATER_THEN_STOCK,
        NO_BOOK_FOUND
    }

    public CartException.ExceptionType type;

    public CartException(String message, CartException.ExceptionType type) {
        super(message);
        this.type = type;
    }
}
