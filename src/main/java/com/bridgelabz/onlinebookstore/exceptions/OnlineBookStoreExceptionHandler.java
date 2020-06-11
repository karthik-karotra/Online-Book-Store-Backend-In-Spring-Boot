package com.bridgelabz.onlinebookstore.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class OnlineBookStoreExceptionHandler {

    @ExceptionHandler(OnlineBookStoreException.class)
    public ResponseEntity<Object> onlineBookStoreExceptionHandler(OnlineBookStoreException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.ALREADY_REPORTED);
    }

    @ExceptionHandler(AdminException.class)
    public ResponseEntity<Object> adminExceptionHandler(AdminException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.ALREADY_REPORTED);
    }

    @ExceptionHandler(CartException.class)
    public ResponseEntity<Object> cartExceptionHandler(CartException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.ALREADY_REPORTED);
    }

    @ExceptionHandler(JWTException.class)
    public ResponseEntity<Object> jwtExceptionHandler(JWTException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.ALREADY_REPORTED);
    }

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<Object> orderExceptionHandler(OrderException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.ALREADY_REPORTED);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<Object> userExceptionHandler(UserException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.ALREADY_REPORTED);
    }
}
