package com.bridgelabz.onlinebookstore.repository;

import com.bridgelabz.onlinebookstore.models.BookDetails;
import org.springframework.data.repository.CrudRepository;

public interface IOnlineBookStoreRepository extends CrudRepository<BookDetails, Integer> {
}
