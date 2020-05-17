package com.bridgelabz.onlinebookstore.repository;

import com.bridgelabz.onlinebookstore.models.OrderBookDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderBookRepository extends JpaRepository<OrderBookDetails,Integer> {
}
