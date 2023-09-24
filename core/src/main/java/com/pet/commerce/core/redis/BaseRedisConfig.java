package com.pet.commerce.core.redis;

import com.pet.commerce.core.config.CustomStringSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

/**
 * @author Ray
 * @since 2023-3-3
 */
@Configuration
public class BaseRedisConfig {

    @Autowired
    private RedisProperties redisProperties;

    @Autowired
    private CustomStringSerializer customStringSerializer;

    @Autowired
    private CustomNameMapper customNameMapper;

    @Value("${spring.cache.redis.key-prefix}")
    private String keyPrefix;

    /**
     * 缓存管理器
     */
    @ConditionalOnMissingBean
    @Bean
    @Primary
    public CacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
        //初始化一个RedisCacheWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);

        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .computePrefixWith(cacheName -> keyPrefix + cacheName + ":")
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer()))
                //设置默认超过期时间是1天
                .entryTtl(Duration.ofDays(1));
        //初始化RedisCacheManager
        return new CustomRedisCacheManager(redisCacheWriter, defaultCacheConfig);
    }

    /**
     * redis template
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setValueSerializer(jackson2JsonRedisSerializer());
        template.setKeySerializer(customStringSerializer);
        template.afterPropertiesSet();
        return template;
    }

    /**
     * string redis template
     */
    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(factory);
        stringRedisTemplate.setKeySerializer(customStringSerializer);
        return stringRedisTemplate;
    }

    /**
     * object redis template
     */
    @Bean
    public RedisTemplate<String, Object> objectRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(customStringSerializer);
        template.setHashKeySerializer(customStringSerializer);
        template.setValueSerializer(jackson2JsonRedisSerializer());
        return template;
    }

    /**
     * spring session redis template
     */
    @Bean
    public RedisTemplate<Object, Object> springSessionRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        template.setKeySerializer(customStringSerializer);
        template.setHashKeySerializer(customStringSerializer);
        template.setValueSerializer(jackson2JsonRedisSerializer());
        return template;
    }

    protected Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.WRAPPER_ARRAY);
        serializer.setObjectMapper(mapper);
        return serializer;
    }

    /**
     * redisson client
     */
    @Bean(destroyMethod = "shutdown")
    protected RedissonClient redissonClient() {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setNameMapper(customNameMapper);
        singleServerConfig.setAddress(String.format("redis://%s:%s", redisProperties.getHost(), redisProperties.getPort()));
        String password = redisProperties.getPassword();
        if (StringUtils.isNotBlank(password)) {
            singleServerConfig.setPassword(password);
        }
        return Redisson.create(config);
    }

}
