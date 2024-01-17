package com.jake.reddisson.coupon.service;

import com.jake.reddisson.common.aop.DistributedLock;
import com.jake.reddisson.coupon.model.Coupon;
import com.jake.reddisson.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepo;

    @Transactional
    public void useCoupon(Long couponId) {
        Coupon coupon = couponRepo.findById(couponId)
                .orElseThrow(IllegalArgumentException::new);

        coupon.decrease();
    }

    @DistributedLock(key = "#lockName")
    public void useCoupon(String lockName, Long couponId) {
        Coupon coupon = couponRepo.findById(couponId)
                .orElseThrow(IllegalArgumentException::new);

        coupon.decrease();
    }
}
