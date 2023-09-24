package com.pet.commerce.core.redis;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author Ray
 * @since 2023-3-3
 */
public abstract class AbstractCache<V> {

    protected final Logger logger = LoggerFactory.getLogger(AbstractCache.class);

    /**
     * 内存缓存
     */
    protected final LoadingCache<String, V> cache;

    protected final String topicName;

    protected AbstractCache(String topicName, CacheLoader<String, V> cacheLoader) {
        this.topicName = topicName;
        // 初始化缓存
        cache = Caffeine.newBuilder()
                .softValues() // 使用软引用
                .refreshAfterWrite(1, TimeUnit.DAYS)
                .expireAfterWrite(3, TimeUnit.DAYS)
                .maximumSize(100)
                .build(cacheLoader);
    }

    protected AbstractCache(String topicName, RedissonClient redissonClient, CacheLoader<String, V> cacheLoader) {
        this.topicName = topicName;
        // 初始化缓存
        cache = Caffeine.newBuilder()
                .softValues() // 使用软引用
                .refreshAfterWrite(1, TimeUnit.DAYS)
                .expireAfterWrite(3, TimeUnit.DAYS)
                .maximumSize(100)
                .build(cacheLoader);

        // 注册监听器
        redissonClient.getTopic(topicName).addListener(String.class, (channel, msg) -> {
            logger.debug("Listen to channel[{}] with msg: {}", channel, msg);
            // 刷新缓存数据
            cache.refresh(msg);
        });
    }

    /**
     * 获取缓存数据
     */
    public V get(String key) {
        return cache.get(key);
    }


    public LoadingCache<String, V> getCache() {
        return cache;
    }

    public void deleteAll() {
        cache.invalidateAll();
    }

    public void refresh(String key) {
        cache.refresh(key);
    }

}
