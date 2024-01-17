package com.jake.reddisson.coupon.repository;

import com.jake.reddisson.coupon.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
