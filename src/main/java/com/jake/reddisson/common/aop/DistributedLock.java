package com.jake.reddisson.common.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * {@code
 *
 *      @DistributedLock(key ="#lockName")
 *      public void shipment(String lockName) {
 *          ...
 *      }
 *
 *      @DistributedLock(key = "#model.getName().concat('-').concat(#model.getOrderNumber())")
 *      public void shipment(ShipmentModel model) {
 *          ...
 *      }
 *
 *      ShipmentModel.java
 *      public class ShipmentModel {
 *          private String name;
 *          private String orderNumber;
 *
 *          public String getName() {
 *              return this.name;
 *          }
 *
 *          public String getOrderNumber() {
 *              return this.orderNumber;
 *          }
 *      }
 *
 * }
 * </pre>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributedLock {

    String key();

    TimeUnit timeUnit() default TimeUnit.SECONDS;

    long waitTime() default 5L;

    long leaseTime() default 3L;

}

