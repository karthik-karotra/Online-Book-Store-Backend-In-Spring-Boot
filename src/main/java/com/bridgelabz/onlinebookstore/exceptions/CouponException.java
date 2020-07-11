package com.bridgelabz.onlinebookstore.exceptions;

public class CouponException extends RuntimeException {

    public enum ExceptionType {
        USER_NOT_FOUND,
        COUPONS_NOT_AVAILABLE
    }

    public CouponException.ExceptionType type;

    public CouponException(String message, CouponException.ExceptionType type) {
        super(message);
        this.type = type;
    }
}
