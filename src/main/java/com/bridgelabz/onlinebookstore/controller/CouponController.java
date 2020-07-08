package com.bridgelabz.onlinebookstore.controller;

import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.models.Coupons;
import com.bridgelabz.onlinebookstore.service.ICouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class CouponController {

    @Autowired
    ICouponService couponService;

    @GetMapping("/coupons")
    public ResponseEntity fetchOrderCoupon(@RequestHeader(value = "token") String token, @RequestParam(name = "totalPrice") Double totalPrice) {
        List<Coupons> orders = couponService.fetchCoupon(token, totalPrice);
        ResponseDTO response = new ResponseDTO(orders, "Coupons Fetched Successfully");
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
