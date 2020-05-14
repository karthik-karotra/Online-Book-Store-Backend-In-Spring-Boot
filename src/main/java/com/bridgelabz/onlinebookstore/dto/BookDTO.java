package com.bridgelabz.onlinebookstore.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class BookDTO {

    @NotNull
    @Length(min = 10, max = 10, message = "ISBN Number Should Be Of 10 Digit")
    @Pattern(regexp = "^[1-9][0-9]{9}$")
    public String isbn;

    @NotNull
    @Pattern(regexp = "^([a-zA-Z]{3,}[ ]*[a-zA-Z]*[ ]*[a-zA-Z]*[ ]*[a-zA-Z]*[ ]*[a-zA-Z]*[ ]*[a-zA-Z]*[ ]*[a-zA-Z]*)$")
    public String bookName;

    @NotNull
    @Pattern(regexp = "^.{3,50}$")
    public String authorName;

    @NotNull
    @Range(min = 1, message = "Book Price Should Be Greater Than Zero")
    public double bookPrice;

    @NotNull
    @Range(min = 1, message = "Quantity Shoud Be Greater Than Zero")
    public int quantity;

    @NotNull
    public String bookDetails;

    @NotNull
    public String bookImageSource;

    @NotNull
    @Range(min = 1000, message = "Publishing Year Should Be Greater Than 999")
    public int publishingYear;

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
}
