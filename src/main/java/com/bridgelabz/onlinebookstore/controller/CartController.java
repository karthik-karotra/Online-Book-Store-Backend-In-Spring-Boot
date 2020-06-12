package com.bridgelabz.onlinebookstore.controller;

import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.models.BookCart;
import com.bridgelabz.onlinebookstore.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
public class CartController {

    @Autowired
    ICartService cartService;

    @PostMapping("/cart/{quantity}/{bookId}")
    public ResponseEntity<ResponseDTO> saveBook(@PathVariable Integer quantity, @PathVariable Integer bookId ,@RequestHeader(value = "token") String token) {
        String message = cartService.saveBooksToCart(quantity, bookId, token);
        ResponseDTO responseDTO = new ResponseDTO(message);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/cart")
    public ResponseEntity<ResponseDTO> getBooks(@RequestHeader(value = "token") String token) {
        List<BookCart> cartDetailsList = cartService.getAllBooks(token);
        ResponseDTO responseDTO = new ResponseDTO(cartDetailsList, "Response Successful");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/cart/{id}")
    public ResponseEntity<ResponseDTO> deleteFromCart(@PathVariable Integer id, @RequestHeader(value = "token") String token) {
        String message = cartService.deleteBookFromCart(id, token);
        ResponseDTO responseDto = new ResponseDTO(message);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PutMapping("/cart/{bookCartId}/{quantity}")
    public ResponseEntity updateQuantity(@PathVariable Integer bookCartId, @PathVariable Integer quantity, @RequestHeader(value = "token") String token) {
        String message = cartService.updateQuantity(bookCartId, quantity, token);
        ResponseDTO responseDto = new ResponseDTO(message);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
