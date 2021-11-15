package com.demo.eventcounter.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Event {
    public enum TYPE {
        CLICK, VIEW;

        @Override
        public String toString(){
            return name();
        }
    }

    private TYPE type;
    private String uuid;
    private String ip;
    private String url;
}
