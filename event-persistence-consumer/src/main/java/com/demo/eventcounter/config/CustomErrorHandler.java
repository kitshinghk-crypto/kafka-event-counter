package com.demo.eventcounter.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.listener.ErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;

import java.util.List;

@Slf4j
public class CustomErrorHandler implements ErrorHandler {
    @Override
    public void handle(Exception thrownException, List<ConsumerRecord<?, ?>> records,
                       Consumer<?, ?> consumer, MessageListenerContainer container) {
        log.error(thrownException.getMessage(),thrownException);
        String s = thrownException.getMessage().split("Error deserializing key/value for partition ")[1].split(". If needed, please seek past the record to continue consumption.")[0];
        String topics = s.split("-")[0];
        int offset = Integer.valueOf(s.split("offset ")[1]);
        int partition = Integer.valueOf(s.split("-")[1].split(" at")[0]);

        TopicPartition topicPartition = new TopicPartition(topics, partition);
        consumer.seek(topicPartition, offset + 1);
    }

    @Override
    public void handle(Exception e, ConsumerRecord<?, ?> consumerRecord) {
        log.error(e.getMessage(),e);
    }

    @Override
    public void handle(Exception thrownException, ConsumerRecord<?, ?> data, Consumer<?, ?> consumer) {
        log.error(thrownException.getMessage(),thrownException);
        String s = thrownException.getMessage().split("Error deserializing key/value for partition ")[1].split(". If needed, please seek past the record to continue consumption.")[0];
        String topics = s.split("-")[0];
        int offset = Integer.valueOf(s.split("offset ")[1]);
        int partition = Integer.valueOf(s.split("-")[1].split(" at")[0]);

        TopicPartition topicPartition = new TopicPartition(topics, partition);
        consumer.seek(topicPartition, offset + 1);
        System.out.println("OKKKKK");
    }

    @Override
    public void clearThreadState() {
        ErrorHandler.super.clearThreadState();
    }

    @Override
    public boolean isAckAfterHandle() {
        return ErrorHandler.super.isAckAfterHandle();
    }

    @Override
    public void setAckAfterHandle(boolean ack) {
        ErrorHandler.super.setAckAfterHandle(ack);
    }
}
