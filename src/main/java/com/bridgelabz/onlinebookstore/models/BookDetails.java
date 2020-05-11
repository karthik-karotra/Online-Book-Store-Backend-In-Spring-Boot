package com.bridgelabz.onlinebookstore.models;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import javax.persistence.*;

@Entity
@Table
public class BookDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;
    public String isbn;
    public String bookName;
    public String authorName;
    public int quantity;
    public String bookDetails;
    public String bookImage;
    public int publishingYear;
    public double bookPrice;

    public BookDetails(BookDTO bookDTO) {
        this.isbn = bookDTO.isbn;
        this.bookName = bookDTO.bookName;
        this.authorName = bookDTO.authorName;
        this.bookPrice = bookDTO.bookPrice;
        this.quantity = bookDTO.quantity;
        this.bookDetails = bookDTO.bookDetails;
        this.bookImage = bookDTO.bookImageSource;
        this.publishingYear = bookDTO.publishingYear;
    }

    public BookDetails() {
    }

}
