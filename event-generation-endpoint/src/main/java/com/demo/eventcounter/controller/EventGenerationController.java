package com.demo.eventcounter.controller;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.*;
import com.demo.eventcounter.dto.domain.Event;

@Slf4j
@RestController
@Api(tags = "Event")
@RequestMapping(path="event_generate/")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ComponentScan
public class EventGenerationController {

    @Value("${EventGenerationController.topic}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, Event> kafkaTemplate;

    @PutMapping
    public String createEvent(@RequestBody Event event){
        ListenableFuture<SendResult<String, Event>> resultListenableFuture = kafkaTemplate.send(topic, event);
        resultListenableFuture.addCallback(new ListenableFutureCallback<SendResult<String, Event>>() {

            @Override
            public void onSuccess(SendResult<String, Event> result) {
                log.info("message sent, partition={}, offset={}", result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable throwable) {
                log.warn("failed to send, Event={}", event.toString(), throwable);
            }
        });
        return "OK";
    }
}
