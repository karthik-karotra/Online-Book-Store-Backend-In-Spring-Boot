package com.bridgelabz.onlinebookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
public class BookDTO {

    private int isbn;
    private String bookName;
    private String authorName;
    private double bookPrice;
    private int quantity;
    private String bookDetails;
    private String bookImageSource;
    private int publishingYear;

    public BookDTO(int isbn, String bookName, String authorName, double bookPrice, int quantity, String bookDetails, String bookImageSource, int publishingYear) {
        this.isbn = isbn;
        this.bookName = bookName;
        this.authorName = authorName;
        this.bookPrice = bookPrice;
        this.quantity = quantity;
        this.bookDetails = bookDetails;
        this.bookImageSource = bookImageSource;
        this.publishingYear = publishingYear;
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

    public double getBookPrice() {
        return bookPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getBookDetails() {
        return bookDetails;
    }

    public String getBookImageSource() {
        return bookImageSource;
    }

    public int getPublishingYear() {
        return publishingYear;
    }

}
