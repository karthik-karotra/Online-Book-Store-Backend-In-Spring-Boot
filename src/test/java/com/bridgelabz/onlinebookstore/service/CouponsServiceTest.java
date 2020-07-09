package com.bridgelabz.onlinebookstore.service;

import com.bridgelabz.onlinebookstore.dto.UserLoginDTO;
import com.bridgelabz.onlinebookstore.exceptions.CouponException;
import com.bridgelabz.onlinebookstore.models.Coupons;
import com.bridgelabz.onlinebookstore.models.CouponsDetails;
import com.bridgelabz.onlinebookstore.models.UserDetails;
import com.bridgelabz.onlinebookstore.properties.ApplicationProperties;
import com.bridgelabz.onlinebookstore.repository.CouponDetailsRepository;
import com.bridgelabz.onlinebookstore.repository.CouponRepository;
import com.bridgelabz.onlinebookstore.repository.UserRepository;
import com.bridgelabz.onlinebookstore.service.implementations.CouponService;
import com.bridgelabz.onlinebookstore.utils.ITokenGenerator;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CouponsServiceTest {
    @MockBean
    CouponRepository couponRepository;

    @MockBean
    CouponDetailsRepository couponDetailsRepository;

    @MockBean
    UserRepository userRepository;

    @Autowired
    CouponService couponService;

    @MockBean
    ApplicationProperties applicationProperties;

    @MockBean
    ITokenGenerator jwtToken;

    @Test
    void givenCoupon_WhenFetchCoupon_ShouldReturnCoupons() {
        List<Coupons> couponsList = new ArrayList<>();
        Coupons coupons = new Coupons("WEL100", 100.0, "10% Off upto Rs.100 on minimum purchase of Rs.699.0", "30-06-2020", 699.0);
        couponsList.add(coupons);
        couponsList.add(coupons);
        couponsList.add(coupons);
        UserLoginDTO userLoginDTO = new UserLoginDTO("karthik@gmail.com", "Karthik@123");
        UserDetails userDetails = new UserDetails(userLoginDTO);
        when(jwtToken.getId(any())).thenReturn(1);
        when(couponRepository.findAll()).thenReturn(couponsList);
        List<Coupons> coupons1 = couponService.fetchCoupon("token", 800.0);
        Assert.assertEquals(couponsList, coupons1);
    }

    @Test
    void givenCoupon_WhenNoCouponAvailable_ShouldThrowException() {
        List<CouponsDetails> couponsDetailsList = new ArrayList<>();
        List<Coupons> couponsList = new ArrayList<>();
        Coupons coupons = new Coupons("WEL100", 100.0, "10% Off upto Rs.100 on minimum purchase of Rs.699.0", "30-06-2020", 699.0);
        UserLoginDTO userLoginDTO = new UserLoginDTO("abhishek@gmail.com", "Abhishek@123");
        UserDetails userDetails = new UserDetails(userLoginDTO);
        CouponsDetails couponsDetails = new CouponsDetails(coupons, userDetails);
        couponsDetailsList.add(couponsDetails);
        String message = "Coupons Not Available";
        try {
            when(jwtToken.getId(anyString())).thenReturn(1);
            when(couponRepository.findAll()).thenReturn(couponsList);
            when(couponDetailsRepository.findByUserId(anyInt())).thenReturn(couponsDetailsList);
            couponService.fetchCoupon("token", 800.0);
        } catch (CouponException e) {
            Assert.assertEquals(message, e.getMessage());
        }
    }

    @Test
    void givenCoupon_WhenAppliedOneCoupon_ShouldReturnRemainingCoupon() {
        List<CouponsDetails> couponsDetailsList = new ArrayList<>();
        List<Coupons> couponsList = new ArrayList<>();
        Coupons coupons = new Coupons("WEL100", 100.0, "10% Off upto Rs.100 on minimum purchase of Rs.699.0", "30-06-2020", 699.0);
        couponsList.add(coupons);
        couponsList.add(coupons);
        couponsList.add(coupons);
        UserLoginDTO userLoginDTO = new UserLoginDTO("kalyani@gmail.com", "Kalyani@123");
        UserDetails userDetails = new UserDetails(userLoginDTO);
        CouponsDetails couponsDetails = new CouponsDetails(coupons, userDetails);
        couponsDetailsList.add(couponsDetails);
        when(jwtToken.getId(anyString())).thenReturn(1);
        when(couponRepository.findAll()).thenReturn(couponsList);
        when(couponDetailsRepository.findByUserId(anyInt())).thenReturn(couponsDetailsList);
        List<Coupons> coupons1 = couponService.fetchCoupon("token", 800.0);
        Assert.assertEquals(couponsList.size() - 1, coupons1.size());
    }

    @Test
    void givenCoupon_WhenUserApplied100RsDisCountCoupon_ShouldReturnDiscountPrice() {
        Coupons coupons = new Coupons("WEL100", 100.0, "10% Off upto Rs.100 on minimum purchase of Rs.699.0", "30-06-2020", 699.0);
        UserLoginDTO userLoginDTO = new UserLoginDTO("gajanan@gmail.com", "Gajanan@123");
        UserDetails userDetails = new UserDetails(userLoginDTO);
        CouponsDetails couponsDetails = new CouponsDetails(coupons, userDetails);
        when(jwtToken.getId(anyString())).thenReturn(1);
        when(userRepository.findById(anyInt())).thenReturn(java.util.Optional.of(userDetails));
        when(couponRepository.findByCouponsType(anyString())).thenReturn(java.util.Optional.of(coupons));
        when(couponDetailsRepository.save(any())).thenReturn(couponsDetails);
        Double token = couponService.addCoupon("token", coupons.couponsType, 200.0);
        Assert.assertEquals(coupons.discountPrice, token);
    }

}
