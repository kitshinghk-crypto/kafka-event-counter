package com.demo.eventcounter.dao.domain;

import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name="event_summary")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EventSummary {
    @EmbeddedId
    private EventSummaryId eventSummaryId;
    private int count;
    private LocalDateTime lastupdate;
}
