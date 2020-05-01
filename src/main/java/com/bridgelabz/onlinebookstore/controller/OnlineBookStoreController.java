package com.bridgelabz.onlinebookstore.controller;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.service.IOnlineBookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OnlineBookStoreController {

    @Autowired
    IOnlineBookStoreService onlineBookStoreService;

    @PostMapping("/onlinebookstore/addbook")
    public void addBook1(@RequestBody BookDTO bookDTO){
        onlineBookStoreService.addBook(bookDTO);
    }
}
