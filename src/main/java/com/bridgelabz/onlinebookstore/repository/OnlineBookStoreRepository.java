package com.bridgelabz.onlinebookstore.repository;

import com.bridgelabz.onlinebookstore.models.BookDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OnlineBookStoreRepository extends JpaRepository<BookDetails, Integer> {
    @Query(value = "select * from book_details where author_name LIKE %:str1% OR book_name LIKE %:str1%", nativeQuery = true)
    List<BookDetails> findByAttribute(@Param("str1") String str1);
}
