package com.demo.eventcounter.controller;

import com.demo.eventcounter.dto.domain.EventCount;
import com.demo.eventcounter.dto.domain.EventTrackerRequest;
import com.demo.eventcounter.dto.domain.EventTrackerResponse;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RestController
@Api(tags = "Event")
@RequestMapping(path="event_track/")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ComponentScan
public class EventCountRealtimeTrackController {
    private static final String[] SUPPORTED_TYPE = {"CLICK","VIEW"};
    @Autowired
    private RedisConnectionFactory factory;

    @PostMapping
    public EventTrackerResponse getCount(@RequestBody EventTrackerRequest request){
        EventTrackerResponse response = new EventTrackerResponse();
        response.setUrl(request.getUrl());
        response.setEvents(Stream.of(SUPPORTED_TYPE).map(type-> {
            RedisAtomicInteger i = new RedisAtomicInteger(getRedisCounter(request, type), factory);
            EventCount c = new EventCount();
            c.setType(type);
            c.setCount(i.get());
            return c;
        }).collect(Collectors.toList()));
        return response;
    }

    private String getRedisCounter(EventTrackerRequest request, String type) {
        return request.getUrl()+ type;
    }
}
