package com.pet.commerce.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Public Redisson Lock
 */
@Slf4j
@Component
public class LockHelper {

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 获取锁 （前缀+参数  确保不同功能内锁不会冲突，不会造成死锁，且可以再次获取锁）
     *
     * @param prefix 功能唯一的前缀标识
     * @param param  锁的参数
     * @return
     */
    public RLock getLock(String prefix, Object param) {
        return redissonClient.getLock(prefix + param);
    }

    /**
     * Lock Member Chat to GPT
     */
    public static final String LOCK_MEMBER_CHAT = "LOCK_MEMBER_CHAT";

    /**
     * Lock Blog by update count
     */
    public static final String LOCK_BLOG_UPDATE_COUNT = "LOCK_BLOG_UPDATE_COUNT";
}
