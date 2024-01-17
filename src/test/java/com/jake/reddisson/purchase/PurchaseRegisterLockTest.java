package com.jake.reddisson.purchase;

import com.jake.reddisson.purchase.repository.PurchaseRepository;
import com.jake.reddisson.purchase.service.PurchaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PurchaseRegisterLockTest {

    @Autowired
    PurchaseService purchaseService;

    @Autowired
    PurchaseRepository purchaseRepo;

    @Test
    void 발주등록_분산락_테스트() throws InterruptedException {
        String 발주_코드 = "PURCHASE_001";

        int numThreads = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(numThreads);

        for(int i = 0; i < numThreads; i++) {
            executorService.submit(() -> {
                try {
                    purchaseService.register(발주_코드, 발주_코드);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Long totalCount = purchaseRepo.countByCode(발주_코드);

        System.out.println("등록된 발주 = " + totalCount);
        assertThat(totalCount).isOne();
    }

    @Test
    void 발주등록_분산락_미적용_테스트() throws InterruptedException {
        String 발주_코드 = "PURCHASE_001";

        int numThreads = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(numThreads);

        for(int i = 0; i < numThreads; i++) {
            executorService.submit(() -> {
                try {
                    purchaseService.register(발주_코드);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Long totalCount = purchaseRepo.countByCode(발주_코드);

        System.out.println("등록된 발주 = " + totalCount);
//        assertThat(totalCount).isOne();
        assertThat(totalCount).isGreaterThan(1);
    }
}
