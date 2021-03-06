package com.demo.eventcounter.service;

import com.demo.eventcounter.dao.EventRepository;
import com.demo.eventcounter.dto.domain.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class EventPersistenceConsumerService {
    @Value("${consumer.topic}")
    private final String TOPIC="test";

    @Autowired
    private EventRepository eventRepository;

    @KafkaListener(topics = {TOPIC}, containerFactory = "kafkaListenerContainerFactoryForEvent")
    public void listenWithHeaders(@Payload Event event,
                                  @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                                  @Header(KafkaHeaders.OFFSET) int offset) {
        log.info("message received, partition={}, offset={}, event={}", partition, offset, event.toString());
        com.demo.eventcounter.dao.domain.Event persistenceEvent =
                new com.demo.eventcounter.dao.domain.Event();
        persistenceEvent.setIp(event.getIp());
        persistenceEvent.setType(event.getType());
        persistenceEvent.setUuid(event.getUuid());
        persistenceEvent.setUrl(event.getUrl());
        persistenceEvent.setDatetime(LocalDateTime.now());
        this.eventRepository.save(persistenceEvent);
    }
}
