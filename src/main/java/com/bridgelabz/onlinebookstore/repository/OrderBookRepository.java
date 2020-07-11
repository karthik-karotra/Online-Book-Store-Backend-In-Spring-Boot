package com.bridgelabz.onlinebookstore.repository;

import com.bridgelabz.onlinebookstore.models.OrderBookDetails;
import com.bridgelabz.onlinebookstore.models.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderBookRepository extends JpaRepository<OrderBookDetails, Integer> {
    List<OrderBookDetails> findByUserDetails(UserDetails userDetails);
}
