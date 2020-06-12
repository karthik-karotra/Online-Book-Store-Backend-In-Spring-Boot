package com.bridgelabz.onlinebookstore.repository;

import com.bridgelabz.onlinebookstore.models.CustomerDetails;
import com.bridgelabz.onlinebookstore.models.OrderBookDetails;
import com.bridgelabz.onlinebookstore.models.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderBookRepository extends JpaRepository<OrderBookDetails, Integer> {
    Optional<OrderBookDetails> findByOrderId(int orderId);
    List<OrderBookDetails> findByUserDetails(UserDetails userDetails);
}
