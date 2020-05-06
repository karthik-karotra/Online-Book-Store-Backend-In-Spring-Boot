package com.bridgelabz.onlinebookstore.dto;

import lombok.Setter;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Setter
public class BookDTO {

    @NotNull
    @Pattern(regexp = "^([1-9])([0-9]{3})$")
    private int isbn;

    @NotNull
    @Pattern(regexp = "^([a-zA-Z]+[ ]*[a-zA-Z]*)$")
    private String bookName;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z]+[ ]*[a-zA-Z]*$")
    private String authorName;

    @NotNull
    @Pattern(regexp = "^([1-9]+)([0-9]*)$")
    private double bookPrice;

    @NotNull
    @Pattern(regexp = "^([1-9]+)([0-9]*)$")
    private int quantity;

    @NotNull
    private String bookDetails;

    @NotNull
    private String bookImageSource;

    @NotNull
    @Pattern(regexp = "^([1-9])([0-9]{3})$")
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
