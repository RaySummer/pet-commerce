package com.pet.commerce.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.nio.charset.Charset;

/**
 * @author Ray
 * @since 2023-3-3
 */
@Slf4j
@Component
public class CustomStringSerializer implements RedisSerializer<String> {

    @Value("${spring.cache.redis.key-prefix}")
    private String redisTemplateCacheNamePrefix;

    private final Charset charset;

    public CustomStringSerializer() {
        this(Charset.forName("UTF8"));
    }

    public CustomStringSerializer(Charset charset) {
        Assert.notNull(charset, "Charset must not be null!");
        this.charset = charset;
    }

    @Override
    public String deserialize(byte[] bytes) {
        String key = new String(bytes, charset);
        if (key.startsWith(redisTemplateCacheNamePrefix)) {
            return key.substring(redisTemplateCacheNamePrefix.length());
        }
        return key;
    }

    @Override
    public byte[] serialize(String key) {
        if (key.startsWith(redisTemplateCacheNamePrefix)) {
            return key.getBytes(charset);
        }

        key = redisTemplateCacheNamePrefix + key;
        return key.getBytes(charset);
    }
}
