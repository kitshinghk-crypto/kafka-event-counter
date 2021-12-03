package com.demo.eventcounter.dao.domain;

import java.time.LocalDate;

public interface EventDailyCountSummary {
    String getUrl();
    LocalDate getDate();
    String getType();
    Integer getHournum();
    Integer getCount();
}
