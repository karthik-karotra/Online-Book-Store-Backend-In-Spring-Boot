package com.bridgelabz.onlinebookstore.dto;

import com.bridgelabz.onlinebookstore.models.BookDetails;

public class ResponseDTO {
    public String message;
    public BookDetails bookDetails;

    public ResponseDTO(String message, BookDetails bookDetails) {
        this.message = message;
        this.bookDetails = bookDetails;
    }

}
