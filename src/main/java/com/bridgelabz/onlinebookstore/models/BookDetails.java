package com.bridgelabz.onlinebookstore.models;

import com.bridgelabz.onlinebookstore.dto.BookDTO;

import javax.persistence.*;

@Entity
@Table
public class BookDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;
    public String bookName;
    public String authorName;
    public int quantity;
    public String bookDetails;
    public String bookImage;
    public int publishingYear;
    public double bookPrice;

    public BookDetails() {
    }

    public BookDetails(BookDTO bookDTO) {
        this.bookName = bookDTO.getBookName();
        this.authorName = bookDTO.getAuthorName();
        this.quantity = bookDTO.getQuantity();
        this.bookDetails = bookDTO.getBookDetails();
        this.bookImage = bookDTO.getBookImageSource();
        this.publishingYear = bookDTO.getPublishingYear();
        this.bookPrice = bookDTO.getBookPrice();
    }

}
