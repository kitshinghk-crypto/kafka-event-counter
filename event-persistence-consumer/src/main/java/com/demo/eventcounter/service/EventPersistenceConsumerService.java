package com.demo.eventcounter.service;

import com.demo.eventcounter.domain.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventPersistenceConsumerService {
    @Value("${consumer.topic}")
    private final String TOPIC="test";

    @KafkaListener(topics = {TOPIC}, containerFactory = "kafkaListenerContainerFactoryForEvent")
    public void listenWithHeaders(@Payload Event event,
                                  @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                                  @Header(KafkaHeaders.OFFSET) int offset) {
        log.info("message received, partition={}, offset={}, event={}", partition, offset, event.toString());
    }
}
