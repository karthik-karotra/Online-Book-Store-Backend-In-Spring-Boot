package com.bridgelabz.onlinebookstore.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class CouponsDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer Id;

    @ManyToOne
    @JoinColumn(name = "couponId")
    public Coupons coupons;

    @ManyToOne
    @JoinColumn(name = "userId")
    public UserDetails user;

    public CouponsDetails(Coupons coupons, UserDetails userDetails) {
        this.coupons = coupons;
        this.user = userDetails;
    }
}
