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

    @PostMapping("/order")
    public ResponseEntity<ResponseDTO> addOrderedBooks(@RequestHeader(value = "token", required = false) String token) {
        String message = orderBookService.addOrderSummary(token);
        ResponseDTO responseDTO = new ResponseDTO(message);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
