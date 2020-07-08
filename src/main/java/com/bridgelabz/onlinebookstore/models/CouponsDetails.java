package com.bridgelabz.onlinebookstore.models;

import javax.persistence.*;

@Entity
@Table
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

    public CouponsDetails() {
    }

    public CouponsDetails(Coupons coupons, UserDetails userDetails) {
        this.coupons = coupons;
        this.user = userDetails;
    }

    public UserDetails getUser() {
        return user;
    }

    public void setUser(UserDetails user) {
        this.user = user;
    }
}
