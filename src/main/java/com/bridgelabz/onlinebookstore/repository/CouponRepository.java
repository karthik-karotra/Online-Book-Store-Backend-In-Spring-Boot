package com.bridgelabz.onlinebookstore.repository;

import com.bridgelabz.onlinebookstore.models.Coupons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupons, Integer> {
    Optional<Coupons> findByCouponsType(String coupons);
}