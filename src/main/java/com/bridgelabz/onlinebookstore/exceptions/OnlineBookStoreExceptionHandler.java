package com.bridgelabz.onlinebookstore.exceptions;

import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.models.BookDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class OnlineBookStoreExceptionHandler {
    @ExceptionHandler(OnlineBookStoreException.class)
    public ResponseEntity<ResponseDTO> onlineBookStoreExceptionHandler(OnlineBookStoreException ex) {
        ResponseDTO responseDTO = new ResponseDTO(ex.getMessage(),null);
        return new ResponseEntity<>(responseDTO, HttpStatus.ALREADY_REPORTED);
    }
}
