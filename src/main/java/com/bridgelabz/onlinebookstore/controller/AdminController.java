package com.bridgelabz.onlinebookstore.controller;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.exceptions.AdminException;
import com.bridgelabz.onlinebookstore.service.IAdminBookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    IAdminBookStoreService adminBookStoreService;

    @PostMapping("/book")
    public ResponseEntity<ResponseDTO> saveBook(@Valid @RequestBody BookDTO bookDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new AdminException("Invalid Data!!!!! Please Enter Valid Data", AdminException.ExceptionType.INVALID_DATA);
        }
        String message = adminBookStoreService.saveBook(bookDTO);
        ResponseDTO responseDTO = new ResponseDTO(message);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/book/image")
    public String uploadBookImageToLocalFileSystem(@RequestParam("file") MultipartFile file) {
        String message = adminBookStoreService.uploadImage(file);
        return message;
    }
}
