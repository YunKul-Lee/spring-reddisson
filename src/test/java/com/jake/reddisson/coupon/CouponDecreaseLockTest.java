package com.jake.reddisson.coupon;

import com.jake.reddisson.coupon.model.Coupon;
import com.jake.reddisson.coupon.repository.CouponRepository;
import com.jake.reddisson.coupon.service.CouponService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CouponDecreaseLockTest {

    @Autowired
    CouponService couponService;

    @Autowired
    CouponRepository couponRepo;

    private Coupon coupon;

    @BeforeEach
    void setUp() {
        coupon = new Coupon("DISCOUNT_10_P", 100L);
        coupon = couponRepo.save(coupon);
    }

    @Test
    void 쿠폰차감_분산락_동시성_테스트() throws InterruptedException {
        int numThreads = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(numThreads);

        for(int i=0; i < numThreads; i++) {
            executorService.submit(() -> {
                try {
                    couponService.useCoupon(coupon.getName(), coupon.getId());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Coupon persistCoupon = couponRepo.findById(coupon.getId())
                .orElseThrow(IllegalArgumentException::new);

        assertThat(persistCoupon.getAvailableStock()).isZero();
        System.out.println("잔여 쿠폰 갯수 = " + persistCoupon.getAvailableStock());
    }

    @Test
    void 쿠폰차감_분산락_미적용_동시성_테스트() throws InterruptedException {
        int numThreads = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(numThreads);

        for (int i = 0; i < numThreads; i++) {
            executorService.submit(() -> {
                try {
                    couponService.useCoupon(coupon.getId());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Coupon persistCoupon = couponRepo.findById(coupon.getId())
                .orElseThrow(IllegalArgumentException::new);

        assertThat(persistCoupon.getAvailableStock()).isZero();
        System.out.println("잔여 쿠폰 갯수 = " + persistCoupon.getAvailableStock());
    }
}
