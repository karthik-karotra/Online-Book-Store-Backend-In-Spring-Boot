package com.bridgelabz.onlinebookstore.models;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
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

    public BookDetails(BookDTO bookDTO) {
        this.isbn = bookDTO.getIsbn();
        this.bookName = bookDTO.getBookName();
        this.authorName = bookDTO.getAuthorName();
        this.bookPrice = bookDTO.getBookPrice();
        this.quantity = bookDTO.getQuantity();
        this.bookDetails = bookDTO.getBookDetails();
        this.bookImage = bookDTO.getBookImageSource();
        this.publishingYear = bookDTO.getPublishingYear();
    }

    public BookDetails() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setBookPrice(double bookPrice) {
        this.bookPrice = bookPrice;
    }
}
