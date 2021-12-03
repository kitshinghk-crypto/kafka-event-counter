package com.demo.eventcounter.dto.domain;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EventDailyCountSummary {
    private String url;
    private LocalDate date;
    private List<DateHourEventCount> dateHourEventCounts;
}
