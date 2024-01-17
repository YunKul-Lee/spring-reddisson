package com.jake.reddisson.common.redis.support;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor
public class DefaultDistributedLockDefinition implements DistributedLockDefinition, Serializable {

    private static final long DEFAULT_WAIT_TIME = 5L;
    private static final long DEFAULT_LEASE_TIME = 3L;
    private static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;

    private String key;
    private long waitTime = DEFAULT_WAIT_TIME;
    private long leaseTime = DEFAULT_LEASE_TIME;
    private TimeUnit timeUnit = DEFAULT_TIME_UNIT;

    public DefaultDistributedLockDefinition(
            final long waitTime,
            final long leaseTime,
            final TimeUnit timeUnit
    ) {
        this.waitTime = waitTime;
        this.leaseTime = leaseTime;
        this.timeUnit = timeUnit;
    }
}
