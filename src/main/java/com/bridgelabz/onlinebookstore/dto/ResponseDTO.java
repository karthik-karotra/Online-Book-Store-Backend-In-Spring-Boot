package com.bridgelabz.onlinebookstore.dto;

import com.bridgelabz.onlinebookstore.models.BookDetails;
import java.util.List;

public class ResponseDTO {
    public String message;
    public BookDetails bookDetails;
    public List<BookDetails> bookList;

    public ResponseDTO(String message, BookDetails bookDetails) {
        this.message = message;
        this.bookDetails = bookDetails;
    }

    public ResponseDTO(List<BookDetails> bookList,String message) {
        this.message = message;
        this.bookList = bookList;
    }
}
