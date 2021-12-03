package com.demo.eventcounter.dao.domain;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Embeddable
public class EventSummaryId implements Serializable {
    private String url;
    private LocalDateTime datehour;
    private String type;
}
