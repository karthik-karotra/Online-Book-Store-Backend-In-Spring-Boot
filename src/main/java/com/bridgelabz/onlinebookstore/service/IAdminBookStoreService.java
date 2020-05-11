package com.bridgelabz.onlinebookstore.service;

import com.bridgelabz.onlinebookstore.dto.BookDTO;

public interface IAdminBookStoreService {
    String saveBook(BookDTO bookDTO);
}
