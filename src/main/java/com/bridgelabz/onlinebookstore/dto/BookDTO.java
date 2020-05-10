package com.bridgelabz.onlinebookstore.dto;

import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Setter
public class BookDTO {

    @NotNull
    @Length(min=10, max=10, message = "ISBN Number Should Be Of 10 Digit")
    @Pattern(regexp="^[1-9][0-9]{9}$")
    private String isbn;

    @NotNull
    @Pattern(regexp = "^([a-zA-Z]{3,}[ ]*[a-zA-Z]*[ ]*[a-zA-Z]*[ ]*[a-zA-Z]*[ ]*[a-zA-Z]*[ ]*[a-zA-Z]*[ ]*[a-zA-Z]*)$")
    private String bookName;

    @NotNull
    @Pattern(regexp = "^.{3,50}$")
    private String authorName;

    @NotNull
    @Range(min = 1, message = "Book Price Should Be Greater Than Zero")
    private double bookPrice;

    @NotNull
    @Range(min = 1, message = "Quantity Shoud Be Greater Than Zero")
    private int quantity;

    @NotNull
    private String bookDetails;

    @NotNull
    private String bookImageSource;

    @NotNull
    @Range(min = 1000, message = "Publishing Year Should Be Greater Than 999")
    private int publishingYear;

    public BookDTO(String isbn, String bookName, String authorName, double bookPrice, int quantity, String bookDetails, String bookImageSource, int publishingYear) {
        this.isbn = isbn;
        this.bookName = bookName;
        this.authorName = authorName;
        this.bookPrice = bookPrice;
        this.quantity = quantity;
        this.bookDetails = bookDetails;
        this.bookImageSource = bookImageSource;
        this.publishingYear = publishingYear;
    }

    public String getIsbn() {
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
