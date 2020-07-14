package com.bridgelabz.onlinebookstore.controller;

import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.models.OrderBookDetails;
import com.bridgelabz.onlinebookstore.service.IOrderBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/bookstore")
public class OrderBookController {

    @Autowired
    IOrderBookService orderBookService;

    @PostMapping("/order/{discountPrice}")
    public ResponseEntity<ResponseDTO> addOrderedBooks(@PathVariable Double discountPrice, @RequestParam(name = "discountCoupon") String coupon, @RequestHeader(value = "token") String token) {
        Integer orderId = orderBookService.addOrderSummary(discountPrice, coupon, token);
        ResponseDTO responseDTO = new ResponseDTO(orderId,"Successfully Placed Order");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<ResponseDTO> getOrderDetails(@RequestHeader(value = "token") String token) {
        List<OrderBookDetails> orderBookDetailsList = orderBookService.getOrders(token);
        ResponseDTO responseDTO = new ResponseDTO(orderBookDetailsList, "Response Successful");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
