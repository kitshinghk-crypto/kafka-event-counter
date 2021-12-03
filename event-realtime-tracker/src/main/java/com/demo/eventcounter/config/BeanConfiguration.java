package com.demo.eventcounter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;

@Configuration
public class BeanConfiguration {
    @Value("${Redis.hostname}")
    private String redisHostname;
    @Value("${Redis.port}")
    private int redisPort;
    @Bean
    public RedisConnectionFactory redisAtomicInteger(){
        RedisStandaloneConfiguration c = new RedisStandaloneConfiguration();
        c.setHostName(redisHostname);
        c.setPort(redisPort);
        JedisConnectionFactory f = new JedisConnectionFactory(c);
        return f;
    }
}
