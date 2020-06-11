package com.bridgelabz.onlinebookstore.exceptions;

public class OrderException extends RuntimeException {
    public enum ExceptionType {
        NO_ORDER_PLACED

    }

    public OrderException.ExceptionType type;

    public OrderException(String message, OrderException.ExceptionType type) {
        super(message);
        this.type = type;
    }
}
