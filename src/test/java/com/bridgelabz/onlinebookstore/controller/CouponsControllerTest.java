package com.bridgelabz.onlinebookstore.controller;

import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.models.Coupons;
import com.bridgelabz.onlinebookstore.properties.ApplicationProperties;
import com.bridgelabz.onlinebookstore.repository.CouponRepository;
import com.bridgelabz.onlinebookstore.service.implementations.CouponService;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(CouponController.class)
public class CouponsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CouponService couponService;

    @MockBean
    CouponRepository couponRepository;

    @MockBean
    ApplicationProperties applicationProperties;

    Gson gson = new Gson();
    HttpHeaders httpHeaders = new HttpHeaders();

    @Test
    void givenCoupon_WhenFetchCoupon_ShouldReturnMessage() throws Exception {
        httpHeaders.set("token", "Qwebst43Y");
        List<Coupons> couponsList = new ArrayList<>();
        List<Coupons> couponsList1 = new ArrayList<>();
        String totalPrice = "350";
        Coupons coupons = new Coupons("WEL100", 100.0, "10% Off upto Rs.100 on minimum purchase of Rs.699.0", "30-06-2020", 699.0);
        couponsList.add(coupons);
        couponsList1.add(coupons);
        String message = "Coupons Fetched Successfully";
        when(couponService.fetchCoupon(anyString(), any())).thenReturn(couponsList);
        MvcResult mvcResult = this.mockMvc.perform(get("/coupons")
                .param("totalPrice", totalPrice)
                .contentType(MediaType.APPLICATION_JSON).headers(httpHeaders).characterEncoding("utf-8")).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        ResponseDTO responseDto = gson.fromJson(response, ResponseDTO.class);
        String responseMessage = responseDto.message;
        Assert.assertEquals(message, responseMessage);
    }

    @Test
    void givenCoupon_WhenFetchCoupons_ShouldReturnAllCoupons() {
        List<Coupons> couponsList = new ArrayList<>();
        List<Coupons> couponsList1 = new ArrayList<>();
        Coupons coupons = new Coupons("WEL100", 100.0, "10% Off upto Rs.100 on minimum purchase of Rs.699.0", "30-06-2020", 699.0);
        couponsList.add(coupons);
        couponsList1.add(coupons);
        when(couponService.fetchCoupon(anyString(), any())).thenReturn(couponsList);
        Assert.assertEquals(couponsList1, couponsList);
    }

    @Test
    void givenCoupon_WhenCouponAdded_ShouldReturnMessage() throws Exception {
        httpHeaders.set("token", "Qwebst43Y");
        String discountCoupon = "CB50";
        String totalPrice = "200.0";
        String message = "Coupon added successfully";
        when(couponService.addCoupon(any(), anyString(), anyDouble())).thenReturn(200.0);
        MvcResult mvcResult = this.mockMvc.perform(post("/order/coupon")
                .param("discountCoupon", discountCoupon).param("totalPrice", totalPrice)
                .contentType(MediaType.APPLICATION_JSON).headers(httpHeaders).characterEncoding("utf-8")).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        ResponseDTO responseDto = gson.fromJson(response, ResponseDTO.class);
        String responseMessage = responseDto.message;
        Assert.assertEquals(message, responseMessage);
    }

}
