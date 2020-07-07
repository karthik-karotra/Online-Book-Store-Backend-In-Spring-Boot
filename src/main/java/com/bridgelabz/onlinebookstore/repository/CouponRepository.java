package com.bridgelabz.onlinebookstore.repository;

import com.bridgelabz.onlinebookstore.models.Coupons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupons, Integer> {
    @Query(value = "select * from coupons where coupons_type <> :couponType", nativeQuery = true)
    List<Coupons> fetchCoupons(@Param("couponType") String couponType);
    Optional<Coupons> findByCouponsType(String coupons);
}