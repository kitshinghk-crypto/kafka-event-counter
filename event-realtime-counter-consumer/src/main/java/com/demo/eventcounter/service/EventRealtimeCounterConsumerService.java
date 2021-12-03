package com.demo.eventcounter.service;

import com.demo.eventcounter.dto.domain.Event;
import com.demo.eventcounter.utils.EventUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Component
@Getter
@Setter
@AllArgsConstructor
@Slf4j
public class EventRealtimeCounterConsumerService {
    private final String TOPIC="test";

    @Autowired
    public RedisConnectionFactory redisConnectionFactory;

    @KafkaListener(topics = {TOPIC}, containerFactory = "kafkaListenerContainerFactoryForEvent")
    public void listenWithHeaders(@Payload Event event,
                                  @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                                  @Header(KafkaHeaders.OFFSET) int offset) {
        log.info("message received, partition={}, offset={}, event={}", partition, offset, event.toString());
        String hashKey = EventUtils.cacheHashkey(event);
        RedisAtomicInteger i = new RedisAtomicInteger(hashKey, redisConnectionFactory);
        int c = i.incrementAndGet();
        log.info("Count plus 1: hashkey={}. curr={}", hashKey, c);
    }
}
