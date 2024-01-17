package com.jake.reddisson.common.redis.support;

import java.util.concurrent.TimeUnit;

public interface DistributedLockDefinition {

    String getKey();

    long getWaitTime();

    long getLeaseTime();

    TimeUnit getTimeUnit();
}
