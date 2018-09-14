package com.vibhor.shorten.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPool;

@Configuration
public class RedisDataConfig {

    public static Logger log = LoggerFactory.getLogger(RedisDataConfig.class);

    @Autowired
    private RedisProperties properties;

    @Bean
    public JedisPool jedisPool() {
        return new JedisPool(properties.getHost(), properties.getPort());
    }

}
