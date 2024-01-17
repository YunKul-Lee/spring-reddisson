package com.jake.reddisson.coupon.model;

import com.sun.source.doctree.EscapeTree;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long availableStock;

    public Coupon(String name, Long availableStock) {
        this.name = name;
        this.availableStock = availableStock;
    }

    public void decrease() {
        validateStockCount();
        this.availableStock -= 1;
    }

    private void validateStockCount() {
        if(availableStock < 1) {
            throw new IllegalArgumentException();
        }
    }
}
