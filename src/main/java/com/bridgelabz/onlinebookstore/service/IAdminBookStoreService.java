package com.bridgelabz.onlinebookstore.service;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.models.BookDetails;

public interface IAdminBookStoreService {
    BookDetails saveBook(BookDTO bookDTO);
}
