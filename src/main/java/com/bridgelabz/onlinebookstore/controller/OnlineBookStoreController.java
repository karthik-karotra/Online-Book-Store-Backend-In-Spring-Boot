package com.bridgelabz.onlinebookstore.controller;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.models.BookDetails;
import com.bridgelabz.onlinebookstore.service.IOnlineBookStoreService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class OnlineBookStoreController {

    @Autowired
    IOnlineBookStoreService onlineBookStoreService;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @PostMapping("/book")
    public ResponseEntity<ResponseDTO> addBook1(@RequestBody BookDTO bookDTO) {
        BookDetails bookDetails = onlineBookStoreService.addBook(bookDTO);
        ResponseDTO responseDTO = new ResponseDTO("ADDED SUCCESSFULLY", bookDetails);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}