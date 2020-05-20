package com.bridgelabz.onlinebookstore.service;

import com.bridgelabz.onlinebookstore.filterenums.FilterAttributes;
import com.bridgelabz.onlinebookstore.models.BookDetails;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import java.util.List;

public interface IOnlineBookStoreService {
    List<BookDetails> getAllBooks(Integer pageNo, Integer pageSize);
    Integer getCountOfBooks();
    Page<BookDetails> findAllBooks(String searchText, int pageNo, FilterAttributes filterAttributes);
    Resource loadFileAsResource(String fileName);
}
