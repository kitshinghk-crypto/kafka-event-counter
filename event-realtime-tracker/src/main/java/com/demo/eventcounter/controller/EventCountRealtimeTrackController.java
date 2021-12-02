package com.demo.eventcounter.controller;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @Autowired
    private RedisAtomicInteger count;
    @GetMapping
    public int getCount(){
        return count.get();
    }
}
