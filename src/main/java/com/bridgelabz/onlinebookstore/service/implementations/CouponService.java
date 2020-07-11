package com.bridgelabz.onlinebookstore.service.implementations;

import com.bridgelabz.onlinebookstore.exceptions.CouponException;
import com.bridgelabz.onlinebookstore.models.Coupons;
import com.bridgelabz.onlinebookstore.models.CouponsDetails;
import com.bridgelabz.onlinebookstore.models.UserDetails;
import com.bridgelabz.onlinebookstore.repository.CouponDetailsRepository;
import com.bridgelabz.onlinebookstore.repository.CouponRepository;
import com.bridgelabz.onlinebookstore.repository.UserRepository;
import com.bridgelabz.onlinebookstore.service.ICouponService;
import com.bridgelabz.onlinebookstore.utils.ITokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CouponService implements ICouponService {

    @Autowired
    CouponRepository couponRepository;

    @Autowired
    ITokenGenerator jwtToken;

    @Autowired
    CouponDetailsRepository couponDetailsRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<Coupons> fetchCoupon(String token, Double totalPrice) {
        int userId = jwtToken.getId(token);
        List<Coupons> coupons = couponRepository.findAll();
        List<Coupons> couponsList = new ArrayList<>();
        for (Coupons coupons1 : coupons) {
            if (coupons1.minimumPrice <= totalPrice) {
                couponsList.add(coupons1);
            }
        }
        List<CouponsDetails> couponsDetails = couponDetailsRepository.findByUserId(userId);
        for (CouponsDetails couponDetails1 : couponsDetails) {
            couponsList.remove(couponDetails1.coupons);
        }
        if (coupons.isEmpty() || couponsList.isEmpty())
            throw new CouponException("Coupons Not Available", CouponException.ExceptionType.COUPONS_NOT_AVAILABLE);
        return couponsList;
    }

    @Override
    public Double addCoupon(String token, String coupon, Double totalPrice) {
        int userId = jwtToken.getId(token);
        UserDetails user = userRepository.findById(userId).orElseThrow(() -> new CouponException("USER NOT FOUND", CouponException.ExceptionType.USER_NOT_FOUND));
        Optional<Coupons> coupons = couponRepository.findByCouponsType(coupon);
        CouponsDetails couponsDetails = new CouponsDetails(coupons.get(), user);
        couponDetailsRepository.save(couponsDetails);
        Double discountPrice = (totalPrice - coupons.get().discountPrice) < 0 ? 0 : (totalPrice - coupons.get().discountPrice);
        return discountPrice;
    }
}