package com.bridgelabz.onlinebookstore.models;

import com.bridgelabz.onlinebookstore.dto.BookDTO;

import javax.persistence.*;

@Entity
@Table
public class BookDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;
    public int isbn;
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
        this.isbn = bookDTO.getIsbn();
        this.bookName = bookDTO.getBookName();
        this.authorName = bookDTO.getAuthorName();
        this.quantity = bookDTO.getQuantity();
        this.bookDetails = bookDTO.getBookDetails();
        this.bookImage = bookDTO.getBookImageSource();
        this.publishingYear = bookDTO.getPublishingYear();
        this.bookPrice = bookDTO.getBookPrice();
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setBookPrice(double bookPrice) {
        this.bookPrice = bookPrice;
    }

    public int getIsbn() {
        return isbn;
    }

    public String getBookName() {
        return bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getBookDetails() {
        return bookDetails;
    }

    public String getBookImage() {
        return bookImage;
    }

    public int getPublishingYear() {
        return publishingYear;
    }

    public double getBookPrice() {
        return bookPrice;
    }
}
