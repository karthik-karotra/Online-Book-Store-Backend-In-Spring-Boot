package com.bridgelabz.onlinebookstore.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table
@Data
@Getter
@Setter
public class CartDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<BookCart> bookCarts;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private UserDetails user;

    public CartDetails(BookCart... bookCarts) {
        for (BookCart bookCart : bookCarts) bookCart.setCart(this);
        this.bookCarts = Stream.of(bookCarts).collect(Collectors.toList());
    }

    public CartDetails() {
    }
}
