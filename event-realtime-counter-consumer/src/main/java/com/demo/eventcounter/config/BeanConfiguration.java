package com.demo.eventcounter.config;

import com.demo.eventcounter.dto.domain.Event;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class BeanConfiguration {
    @Value("${BeanConfiguration.bootstrap.servers.config}")
    private String bootstrap;
    @Value("${BeanConfiguration.groupId}")
    private String groupId;
    @Value("${Redis.hostname}")
    private String redisHostname;
    @Value("${Redis.port}")
    private int redisPort;

    @Bean(name = "consumerFactoryForEvent")
    public ConsumerFactory<String, Event> consumerFactoryForEvent() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        JsonDeserializer<Event> d = new JsonDeserializer<Event>();
        d.addTrustedPackages("*");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), d);
    }

    @Bean(name = "kafkaListenerContainerFactoryForEvent")
    public ConcurrentKafkaListenerContainerFactory<String, Event> kafkaListenerContainerFactoryForEvent() {
        ConcurrentKafkaListenerContainerFactory<String, Event> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryForEvent());
        factory.setErrorHandler(new CustomErrorHandler());
        return factory;
    }

    @Bean
    public RedisConnectionFactory redisAtomicInteger(){
        RedisStandaloneConfiguration c = new RedisStandaloneConfiguration();
        c.setHostName(redisHostname);
        c.setPort(redisPort);
        JedisConnectionFactory f = new JedisConnectionFactory(c);
        return f;
    }
}
