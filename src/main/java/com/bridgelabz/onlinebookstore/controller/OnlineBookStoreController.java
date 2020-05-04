package com.bridgelabz.onlinebookstore.controller;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.models.BookDetails;
import com.bridgelabz.onlinebookstore.service.IOnlineBookStoreService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class OnlineBookStoreController {

    @Autowired
    IOnlineBookStoreService onlineBookStoreService;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @PostMapping("/onlinebookstore/addbook")
    public void addBook1(@RequestBody BookDTO bookDTO) {
        onlineBookStoreService.addBook(bookDTO);
    }
}
