package com.jake.reddisson.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class RedissonConfig {

    @Value("${redis.mode:SINGLE}")
    private String mode;

    @Value("${redis.nodes:redis://localhost:6379}")
    private String[] nodes;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();

        switch (mode) {
            case "SINGLE" -> config.useSingleServer().setAddress(nodes[0]);
            case "CLUSTER" -> config.useClusterServers().setNodeAddresses(Arrays.asList(nodes));
        }

        return Redisson.create(config);
    }
}
