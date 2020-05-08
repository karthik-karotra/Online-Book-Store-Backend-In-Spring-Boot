package com.bridgelabz.onlinebookstore.controller;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.models.BookDetails;
import com.bridgelabz.onlinebookstore.service.IOnlineBookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/bookstore")
public class OnlineBookStoreController {

    @Autowired
    IOnlineBookStoreService onlineBookStoreService;

    @GetMapping("/list")
    public ResponseEntity<ResponseDTO> getBook() {
        List<BookDetails> bookDetailsList=onlineBookStoreService.getAllBooks();
        ResponseDTO responseDTO = new ResponseDTO(bookDetailsList,"Response Successful");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @GetMapping("/count")
    public Integer getCount() {
        return onlineBookStoreService.getCountOfBooks();
    }
}
