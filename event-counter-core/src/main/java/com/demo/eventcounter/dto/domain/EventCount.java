package com.demo.eventcounter.dto.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EventCount {
    private String type;
    private int count;
}
