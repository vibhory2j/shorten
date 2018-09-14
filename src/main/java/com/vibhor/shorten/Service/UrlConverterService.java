package com.vibhor.shorten.Service;

import com.vibhor.shorten.Common.Converter;
import com.vibhor.shorten.Common.RedisData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import java.net.URL;

@Configuration
public class UrlConverterService {

    public static Logger log = LoggerFactory.getLogger(UrlConverterService.class);

    private RedisData redisData;

    public UrlConverterService(RedisData redisData) {
        this.redisData = redisData;
    }

    public String shortenUrl(String localURL, String longUrl) {

        //check if the url is already shortened then return that
        log.info("Shortening {}", longUrl);
        Long id = redisData.generateId();
        String uniqueID = Converter.INSTANCE.shortenUrl(id);
        redisData.saveUrl("url:"+id, longUrl);
        String shortenedURL = formatLocalURL(localURL) + uniqueID;
        return shortenedURL;

    }

    public String getLongURLFromID(String uniqueID) throws Exception {
        Long dictionaryKey = Converter.INSTANCE.getDictionaryKeyFromUniqueID(uniqueID);
        String longUrl = redisData.getUrl(dictionaryKey);
        log.info("Converting shortened URL back to {}", longUrl);
        return longUrl;
    }

    private String formatLocalURL(String localURL) {
        String[] partsOfUrl = localURL.split("/");
        // remove the endpoint
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < partsOfUrl.length - 2; ++i) {
            sb.append(partsOfUrl[i]);
        }
        sb.append('/');
        return sb.toString();
    }

}
