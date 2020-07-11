package com.bridgelabz.onlinebookstore.repository;

import com.bridgelabz.onlinebookstore.models.CustomerDetails;
import com.bridgelabz.onlinebookstore.models.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CustomerDetailsRepository extends JpaRepository<CustomerDetails, Integer> {
    List<CustomerDetails> findByUserDetailsOrderByIdDesc(UserDetails userDetails);
}
