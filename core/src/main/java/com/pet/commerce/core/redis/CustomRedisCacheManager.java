package com.pet.commerce.core.redis;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;

import java.time.Duration;
import java.util.Map;

/**
 * @author Ray
 * @since 2023-3-3
 */
public class CustomRedisCacheManager extends RedisCacheManager {

    private static final String CACHE_NAME_SEPARATOR = "#";
    private final RedisCacheConfiguration defaultCacheConfig;

    @Value("${spring.cache.redis.key-prefix}")
    private String keyPrefix;

    public CustomRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
        this.defaultCacheConfig = defaultCacheConfiguration;
    }

    public CustomRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration,
                                   Map<String, RedisCacheConfiguration> initialCacheConfigurations) {

        super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations, true);
        this.defaultCacheConfig = defaultCacheConfiguration;
    }


    @Override
    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
        Duration ttl = defaultCacheConfig.getTtl();
        //名称中存在#标记进行到期时间配置
        if (StringUtils.isNotBlank(name) && name.contains(CACHE_NAME_SEPARATOR)) {
            String[] nameSplit = name.split(CACHE_NAME_SEPARATOR);
            if (StringUtils.isNumeric(nameSplit[1])) {
                // 配置缓存到期时间
                int cycle = Integer.parseInt(nameSplit[1]);
                ttl = Duration.ofSeconds(cycle);
                name = nameSplit[0];
            }
        }
        return super.createRedisCache(name,
                cacheConfig.entryTtl(ttl)
                        .computePrefixWith(cacheName -> keyPrefix + cacheName + ":")
                        .serializeValuesWith(defaultCacheConfig.getValueSerializationPair()));
    }

}
