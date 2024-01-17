package com.jake.reddisson.purchase.service;

import com.jake.reddisson.common.aop.DistributedLock;
import com.jake.reddisson.purchase.model.Purchase;
import com.jake.reddisson.purchase.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepo;

    @DistributedLock(key = "#lockName")
    public void register(String lockName, String code) {
        boolean existsPurchase = purchaseRepo.existsByCode(code);
        if(existsPurchase) {
            throw new IllegalArgumentException();
        }

        Purchase purchase = new Purchase(code);
        purchaseRepo.save(purchase);
    }

    @Transactional
    public void register(String code) {
        boolean existsPurchase = purchaseRepo.existsByCode(code);
        if(existsPurchase) {
            throw new IllegalArgumentException();
        }

        Purchase purchase = new Purchase(code);
        purchaseRepo.save(purchase);
    }
}
