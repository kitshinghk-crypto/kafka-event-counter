package com.demo.eventcounter.dto.domain;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EventTrackerResponse {
    private String url;
    private List<EventCount> events;
}
