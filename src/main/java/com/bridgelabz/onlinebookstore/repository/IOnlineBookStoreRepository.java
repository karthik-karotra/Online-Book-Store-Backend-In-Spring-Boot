package com.bridgelabz.onlinebookstore.repository;

import com.bridgelabz.onlinebookstore.models.BookDetails;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IOnlineBookStoreRepository extends PagingAndSortingRepository<BookDetails, Integer> {
}
