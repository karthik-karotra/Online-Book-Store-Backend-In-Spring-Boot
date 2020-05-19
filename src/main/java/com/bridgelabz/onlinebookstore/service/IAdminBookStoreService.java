package com.bridgelabz.onlinebookstore.service;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import org.springframework.web.multipart.MultipartFile;

public interface IAdminBookStoreService {
    String saveBook(BookDTO bookDTO);
    String uploadImage(MultipartFile file);
}
