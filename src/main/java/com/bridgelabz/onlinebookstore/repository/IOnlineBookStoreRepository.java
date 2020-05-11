package com.bridgelabz.onlinebookstore.repository;

import com.bridgelabz.onlinebookstore.models.BookDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOnlineBookStoreRepository extends JpaRepository<BookDetails, Integer> {
}
