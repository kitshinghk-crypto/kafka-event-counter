package com.demo.eventcounter.utils;

import com.demo.eventcounter.dto.domain.Event;

public class EventUtils {
    public static String cacheHashkey(Event event){
        return event.getUrl()+event.getType();
    }
}
