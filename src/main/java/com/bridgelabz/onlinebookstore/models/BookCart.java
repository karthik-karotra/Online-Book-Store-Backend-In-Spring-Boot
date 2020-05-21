package com.bridgelabz.onlinebookstore.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class BookCart implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer bookCartID;

    @ManyToOne
    @JoinColumn
    private BookDetails book;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private CartDetails cart;

    private Integer quantity;

    public BookCart(BookDetails book, Integer quantity) {
        this.book = book;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookCart bookCart = (BookCart) o;
        return book.equals(bookCart.book) &&
                cart.equals(bookCart.cart) &&
                quantity.equals(bookCart.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(book, cart, quantity);
    }
}
