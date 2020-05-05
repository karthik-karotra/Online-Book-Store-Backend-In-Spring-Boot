package com.bridgelabz.onlinebookstore.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class OnlineBookStoreExceptionHandler {
    @ExceptionHandler(OnlineBookStoreException.class)
    public ResponseEntity<String> onlineBookStoreExceptionHandler(OnlineBookStoreException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.ALREADY_REPORTED);
    }
}
