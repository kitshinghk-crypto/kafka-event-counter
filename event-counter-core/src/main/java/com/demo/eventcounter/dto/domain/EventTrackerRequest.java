package com.demo.eventcounter.dto.domain;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EventTrackerRequest {
    private String url;
}
