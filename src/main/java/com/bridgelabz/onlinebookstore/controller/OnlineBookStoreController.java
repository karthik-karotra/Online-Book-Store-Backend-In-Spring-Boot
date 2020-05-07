package com.bridgelabz.onlinebookstore.controller;

import com.bridgelabz.onlinebookstore.models.BookDetails;
import com.bridgelabz.onlinebookstore.service.IOnlineBookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/bookstore")
public class OnlineBookStoreController {

    @Autowired
    IOnlineBookStoreService onlineBookStoreService;

    @GetMapping("/list")
    public List<BookDetails> getBook() {
        return onlineBookStoreService.getAllBooks();
    }

    @GetMapping("/count")
    public Integer getCount() {
        return onlineBookStoreService.getCountOfBooks();
    }
}
