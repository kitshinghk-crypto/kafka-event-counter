package com.demo.eventcounter.dto.domain;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DateHourEventCount {
    private int datehour;
    private List<EventCount> eventCounts;
}
