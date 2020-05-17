package com.bridgelabz.onlinebookstore.exceptions;

import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class OnlineBookStoreExceptionHandler {

    @ExceptionHandler(OnlineBookStoreException.class)
    public ResponseEntity<Object> onlineBookStoreExceptionHandler(OnlineBookStoreException ex) {
       // ResponseDTO responseDTO = new ResponseDTO(ex.getMessage(), null);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.ALREADY_REPORTED);
    }
}
