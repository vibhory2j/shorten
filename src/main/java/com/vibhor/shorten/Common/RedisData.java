package com.vibhor.shorten.Common;

import com.vibhor.shorten.Config.RedisDataConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

@Configuration
public class RedisData {

    public static Logger log = LoggerFactory.getLogger(RedisData.class);

    @Autowired
    private JedisPool redisPool;

    public RedisData(JedisPool redisPool) {
        this.redisPool = redisPool;
    }

    public Long generateId() {

        Long id;

        try (Jedis jedis = redisPool.getResource()) {

            id = jedis.incr("id:");
        }

        return id - 1;
    }

    public void saveUrl(String key, String longUrl) {

        log.info("Saving: {} at {}", longUrl, key);

        try (Jedis jedis = redisPool.getResource()) {

            jedis.hset("url:", key, longUrl);
        }
    }

    public void saveStatistics(String key) {

        log.info("Saving: at {}", key);

        try (Jedis jedis = redisPool.getResource()) {

            jedis.hincrBy("hits:", key, 1);
        }
    }

    public String getUrl(Long id) throws Exception {
        log.info("Retrieving at {}", id);

        String url = null;

        try (Jedis jedis = redisPool.getResource()) {
            url = jedis.hget("url:", "url:"+id);
            if (url == null) {
                throw new Exception("URL at key" + id + " does not exist");
            }
            else {
                saveStatistics(url+":");
            }
        }

        return url;
    }

    //Can be used to validate if a url is already shortened or not
    public Boolean valueExsitsinHash(String value) {
        log.info("Looking for {} in Redis", value);

        List<String> val = null;

        try (Jedis jedis = redisPool.getResource()) {
            val = jedis.hvals("url:");

            if (val.contains(value))
                return true;
            else
                return false;
        }
    }


}
