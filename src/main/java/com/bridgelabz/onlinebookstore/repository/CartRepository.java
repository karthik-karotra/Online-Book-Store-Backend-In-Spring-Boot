package com.bridgelabz.onlinebookstore.repository;

import com.bridgelabz.onlinebookstore.models.CartDetails;
import com.bridgelabz.onlinebookstore.models.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartDetails, Integer> {
    Optional<CartDetails> findByUser(UserDetails user);
}
