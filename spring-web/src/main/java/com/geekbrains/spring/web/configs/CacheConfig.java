//package com.geekbrains.spring.web.configs;
//
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializationContext;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//import java.time.Duration;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//
//@Configuration
//@EnableCaching
//@EnableRedisRepositories
//public class CacheConfig {
//    private static final long DEFAULT_EXPIRE_TIME = 604800; //неделя
//    private static final long USER_EXPIRE_TIME = 18144000;  //месяц
//    private static final String CARTS_CACHE = "CartsCache";
//
//    @Bean
//    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
//        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
//
//        RedisSerializationContext.SerializationPair<String> keysSerializer =
//                RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer());
//
//        RedisSerializationContext.SerializationPair<Object> valueSerializer =
//                RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer());
//
//        config = config.entryTtl(Duration.ofSeconds(DEFAULT_EXPIRE_TIME))
//                .serializeKeysWith(keysSerializer)
//                .serializeValuesWith(valueSerializer)
//                .disableCachingNullValues();
//
//        Set<String> cacheNames = new HashSet<>();
//        cacheNames.add(CARTS_CACHE);
//
//        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
//        configMap.put(CARTS_CACHE, config.entryTtl(Duration.ofSeconds(USER_EXPIRE_TIME)));
//
//        RedisCacheManager cacheManager = RedisCacheManager.builder(redisConnectionFactory)
//                .cacheDefaults(config)
//                .initialCacheNames(cacheNames)
//                .withInitialCacheConfigurations(configMap)
//                .build();
//
//        return cacheManager;
//    }
//
//}
