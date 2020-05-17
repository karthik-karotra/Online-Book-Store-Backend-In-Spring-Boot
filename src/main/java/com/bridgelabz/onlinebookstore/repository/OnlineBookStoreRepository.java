package com.bridgelabz.onlinebookstore.repository;

import com.bridgelabz.onlinebookstore.models.BookDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OnlineBookStoreRepository extends JpaRepository<BookDetails, Integer> {
    @Query(value = "select * from book_details where author_name LIKE %:toSearch% OR book_name LIKE %:toSearch%", nativeQuery = true)
    Page<BookDetails> findAllBooks(Pageable pageable, @Param("toSearch") String toSearch);


    @Query(value = "select * from book_details where author_name LIKE %:toSearch% OR book_name LIKE %:toSearch%", nativeQuery = true)
    List<BookDetails> findAllBooks(@Param("toSearch") String toSearch);
}
