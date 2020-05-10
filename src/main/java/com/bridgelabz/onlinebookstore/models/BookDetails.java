package com.bridgelabz.onlinebookstore.models;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import lombok.Getter;
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
    public String isbn;
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

}
