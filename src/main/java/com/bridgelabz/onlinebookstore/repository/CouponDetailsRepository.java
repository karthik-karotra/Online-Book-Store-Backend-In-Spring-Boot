package com.bridgelabz.onlinebookstore.repository;

import com.bridgelabz.onlinebookstore.models.CouponsDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CouponDetailsRepository extends JpaRepository<CouponsDetails, Integer> {
    List<CouponsDetails> findByUserId(Integer id);
}
