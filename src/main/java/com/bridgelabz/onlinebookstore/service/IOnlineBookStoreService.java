package com.bridgelabz.onlinebookstore.service;

import com.bridgelabz.onlinebookstore.models.BookDetails;
import java.util.List;

public interface IOnlineBookStoreService {
    List<BookDetails> getAllBooks();
}
