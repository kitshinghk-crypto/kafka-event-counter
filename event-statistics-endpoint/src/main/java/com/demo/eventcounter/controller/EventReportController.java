package com.demo.eventcounter.controller;

import com.demo.eventcounter.dao.repository.EventSummaryRepository;
import com.demo.eventcounter.dto.domain.DateHourEventCount;
import com.demo.eventcounter.dto.domain.EventCount;
import com.demo.eventcounter.dto.domain.EventDailyCountRequest;
import com.demo.eventcounter.dto.domain.EventDailyCountSummary;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@Api(tags = "Event")
@RequestMapping(path="event_report/")
@Getter
@Setter
@AllArgsConstructor
@ComponentScan
public class EventReportController {

    @Autowired
    private EventSummaryRepository eventSummaryRepository;

    @PostMapping
    @Transactional
    public EventDailyCountSummary getSummary(@RequestBody EventDailyCountRequest request){
        List<com.demo.eventcounter.dao.domain.EventDailyCountSummary> eventDailyCountSummary =
                this.eventSummaryRepository.getEventDailyCountSummary(request.getDate(), request.getUrl());
        EventDailyCountSummary result = new EventDailyCountSummary();
        result.setUrl(request.getUrl());
        result.setDate(request.getDate());
        log.info("Return summary: url={}, date={}", request.getUrl(), request.getDate().toString());
        if (eventDailyCountSummary!=null){
            List<DateHourEventCount> counts = eventDailyCountSummary.stream()
                    .collect(Collectors.toMap(com.demo.eventcounter.dao.domain.EventDailyCountSummary::getHournum, s -> {
                        List<EventCount> list = new ArrayList<EventCount>();
                        list.add(new EventCount(s.getType(), s.getCount()));
                        log.info("hournum={}, type={}, count={}", s.getHournum(), s.getType(), s.getCount());
                        return list;
                    }, (v1, v2) -> {
                        v1.addAll(v2);
                        return v1;
                    })).entrySet().stream()
                    .map(s -> {
                        log.info("s={}", s);
                        DateHourEventCount c = new DateHourEventCount();
                        c.setDatehour(s.getKey());
                        c.setEventCounts(s.getValue());
                        return c;
                    }).collect(Collectors.toList());
            result.setDateHourEventCounts(counts);
        }
        return result;
    }
}
