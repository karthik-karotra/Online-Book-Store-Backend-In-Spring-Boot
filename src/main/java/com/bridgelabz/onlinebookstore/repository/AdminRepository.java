package com.bridgelabz.onlinebookstore.repository;

import com.bridgelabz.onlinebookstore.models.BookDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<BookDetails, Integer> {
    Optional<BookDetails> findByIsbn(String isbn);
    Optional<BookDetails> findByBookNameAndAuthorName(String bookName, String authorName);
}
