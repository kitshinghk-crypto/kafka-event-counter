package com.demo.eventcounter.dto.domain;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EventDailyCountRequest {
    private String url;
    private LocalDate date;
}
