package com.pet.commerce.core.redis;

import org.redisson.api.NameMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Ray
 * @since 2023-3-3
 */
@Component
public class CustomNameMapper implements NameMapper {

    @Value("${spring.cache.redis.key-prefix}")
    private String redisTemplateCacheNamePrefix;

    @Override
    public String map(String name) {
        if (name.startsWith(redisTemplateCacheNamePrefix)) {
            return name;
        }

        return redisTemplateCacheNamePrefix + name;
    }

    @Override
    public String unmap(String name) {
        if (name.startsWith(redisTemplateCacheNamePrefix)) {
            return name.substring(redisTemplateCacheNamePrefix.length());
        }
        return name;
    }

}
