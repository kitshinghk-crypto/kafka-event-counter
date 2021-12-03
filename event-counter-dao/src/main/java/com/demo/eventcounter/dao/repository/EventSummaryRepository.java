package com.demo.eventcounter.dao.repository;

import com.demo.eventcounter.dao.domain.EventDailyCountSummary;
import com.demo.eventcounter.dao.domain.EventSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventSummaryRepository extends JpaRepository<EventSummary, String> {
    @Modifying
    @Query(value =
            "INSERT INTO EVENT_SUMMARY " +
            "SELECT url, date_trunc('hour', datetime) as datehour, type, count(1) as count , now() as lastupdate " +
            "FROM event " +
            "WHERE date_trunc('hour', datetime) = :hour " +
            "GROUP BY url, datehour, type"
            ,nativeQuery = true)
    public void aggregateEventSummary(LocalDateTime hour);

    public List<EventSummary> removeByEventSummaryIdDatehour(LocalDateTime datehour);

    @Query(value = "SELECT ue.url, ue.type, current_date as date, dh.hour_num as hournum, COALESCE(es.count,0) as count " +
            "FROM url_event ue " +
            "CROSS JOIN date_hour dh " +
            "LEFT JOIN EVENT_SUMMARY es ON ue.url = es.url AND ue.type = es.type " +
            "AND dh.hour_num = extract(hour from es.datehour) and date(es.datehour) = :date " +
            "WHERE ue.url = :url "
            ,nativeQuery = true)
    public List<EventDailyCountSummary> getEventDailyCountSummary(LocalDate date, String url);
}
