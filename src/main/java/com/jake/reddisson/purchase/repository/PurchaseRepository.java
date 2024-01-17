package com.jake.reddisson.purchase.repository;

import com.jake.reddisson.purchase.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    boolean existsByCode(String code);

    Long countByCode(String code);
}
