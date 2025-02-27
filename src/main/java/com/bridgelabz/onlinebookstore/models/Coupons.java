package com.bridgelabz.onlinebookstore.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Coupons {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer Id;

    public String couponsType;
    public Double discountPrice;
    public String description;
    public String expireCouponDate;
    public Double minimumPrice;

    @JsonIgnore
    @OneToMany(mappedBy = "coupons")
    public List<CouponsDetails> couponsDetails;

    @JsonIgnore
    @OneToMany(mappedBy = "coupons")
    public List<OrderBookDetails> orderBookDetails;

    public Coupons(String couponsType, Double discountPrice, String description, String expireCouponDate, Double minimumPrice) {
        this.couponsType = couponsType;
        this.discountPrice = discountPrice;
        this.description = description;
        this.expireCouponDate = expireCouponDate;
        this.minimumPrice = minimumPrice;
    }
}
