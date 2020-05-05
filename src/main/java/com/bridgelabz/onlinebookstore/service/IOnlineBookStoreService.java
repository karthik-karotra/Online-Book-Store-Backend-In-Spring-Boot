package com.bridgelabz.onlinebookstore.service;
import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.models.BookDetails;

import java.util.List;

public interface IOnlineBookStoreService {
    BookDetails addBook(BookDTO bookDTO);
}
