package com.demo.eventcounter.dto.domain;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Event {
    private String uuid;
    private String type;
    private String ip;
    private String url;
}
