package com.jake.reddisson.common.redis.support;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Set;
import java.util.function.Supplier;

@Slf4j
public class DistributedLockTemplate extends DefaultDistributedLockDefinition implements DistributedLockOperations {

    private final RedissonClient redissonClient;
    private final TransactionTemplate transactionTemplate;

    public DistributedLockTemplate(
            final RedissonClient redissonClient,
            final TransactionTemplate transactionTemplate
    ) {
        this.redissonClient = redissonClient;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public <T> T execute(Supplier<T> supplier) {
        // TODO ::

        return null;
    }

    @Override
    public <T> T execute(String prefix, Set<Long> keys, Supplier<T> supplier) {
        // TODO ::

        return null;
    }


}
