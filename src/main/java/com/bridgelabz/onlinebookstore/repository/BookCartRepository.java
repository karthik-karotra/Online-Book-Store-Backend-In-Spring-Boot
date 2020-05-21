package com.bridgelabz.onlinebookstore.repository;

import com.bridgelabz.onlinebookstore.models.BookCart;
import com.bridgelabz.onlinebookstore.models.CartDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookCartRepository extends JpaRepository<BookCart, Integer> {
    List<BookCart> findAllByCart(CartDetails cartDetails);
}
