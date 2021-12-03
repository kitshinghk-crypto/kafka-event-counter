package com.demo.eventcounter.service;


import com.demo.eventcounter.dao.repository.EventSummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class DataAggregationService {
    @Autowired
    private EventSummaryRepository eventSummaryRepository;

    @Scheduled(cron = "0 0,5,10,15,20,25,30,35,40,45,50,55 * * * *")
    @Transactional
    public void aggreate(){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime hour = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(),
                now.getHour(), 0);
        this.eventSummaryRepository.removeByEventSummaryIdDatehour(hour);
        this.eventSummaryRepository.aggregateEventSummary(hour);
    }
}
