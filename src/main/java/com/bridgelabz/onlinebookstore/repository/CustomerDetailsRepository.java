package com.bridgelabz.onlinebookstore.repository;

import com.bridgelabz.onlinebookstore.models.CustomerDetails;
import com.bridgelabz.onlinebookstore.models.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CustomerDetailsRepository extends JpaRepository<CustomerDetails,Integer> {
}
