package com.bridgelabz.onlinebookstore.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Data
@Getter
@Setter
@NoArgsConstructor
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

}
