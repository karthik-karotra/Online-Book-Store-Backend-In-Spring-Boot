package com.bridgelabz.onlinebookstore.service;


import com.bridgelabz.onlinebookstore.models.Coupons;

import java.util.List;

public interface ICouponService {
    List<Coupons> fetchCoupon(String token, Double totalPrice);
    Double addCoupon(String token, String coupon, Double totalPrice);
}
