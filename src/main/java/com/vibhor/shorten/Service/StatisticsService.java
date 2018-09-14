package com.vibhor.shorten.Service;

import com.vibhor.shorten.Common.RedisData;
import com.vibhor.shorten.Model.ShortenUrl;
import com.vibhor.shorten.Model.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

@Configuration
public class StatisticsService {

    public static Logger log = LoggerFactory.getLogger(StatisticsService.class);

    private RedisData redisData;

    private JedisPool redisPool;

    @Autowired
    public StatisticsService(RedisData redisData, JedisPool redisPool) {
        this.redisPool = redisPool;
        this.redisData = redisData;
    }

    public Statistics generateAllStatistics() {

        Statistics statistics = new Statistics();

        try (Jedis jedis = redisPool.getResource()) {
            HashMap<String, String> stats = (HashMap)jedis.hgetAll("hits:");
            ArrayList<ShortenUrl> list = new ArrayList<>();
            Set<String> set = stats.keySet();
            for (String key : set) {
                log.info("url: " + key);
                ShortenUrl shortenUrl = new ShortenUrl();
                shortenUrl.setUrl(key);
                shortenUrl.setHits(stats.get(key));
                list.add(shortenUrl);
            }
            statistics.setShortenUrls(list);
        }
        return statistics;
    }
}
