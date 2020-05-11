package com.bridgelabz.onlinebookstore.controller;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.service.IAdminBookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    IAdminBookStoreService adminBookStoreService;

    @PostMapping("/book")
    public ResponseEntity<ResponseDTO> saveBook(@RequestBody BookDTO bookDTO) {
        adminBookStoreService.saveBook(bookDTO);
        ResponseDTO responseDTO = new ResponseDTO("ADDED SUCCESSFULLY",null);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}