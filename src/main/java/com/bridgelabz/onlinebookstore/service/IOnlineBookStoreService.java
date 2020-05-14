package com.bridgelabz.onlinebookstore.service;

import com.bridgelabz.onlinebookstore.filterenums.FilterAttributes;
import com.bridgelabz.onlinebookstore.models.BookDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOnlineBookStoreService {
    List<BookDetails> getAllBooks(Integer pageNo, Integer pageSize);
    Integer getCountOfBooks();
    Page<BookDetails> searchBooks(Pageable pageable, String searchText);
    List<BookDetails> findAllBooks(String text, int pageNo, FilterAttributes filterAttributes);
}
