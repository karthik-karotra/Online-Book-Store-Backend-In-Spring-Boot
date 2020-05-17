package com.bridgelabz.onlinebookstore.controller;

import com.bridgelabz.onlinebookstore.dto.OrderBookDTO;
import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.exceptions.OnlineBookStoreException;
import com.bridgelabz.onlinebookstore.models.OrderBookDetails;
import com.bridgelabz.onlinebookstore.service.IOrderBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/bookstore")
public class OrderBookController {

    @Autowired
    IOrderBookService orderBookService;

    @PostMapping("/order")
    public ResponseEntity<ResponseDTO> addOrderedBook(@Valid @RequestBody OrderBookDTO orderBookDTO) {
        OrderBookDetails orderBookDetails = orderBookService.addOrderSummary(orderBookDTO);
        ResponseDTO responseDTO = new ResponseDTO("Added Successfully", orderBookDetails);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
