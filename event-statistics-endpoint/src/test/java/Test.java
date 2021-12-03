import com.demo.eventcounter.dao.domain.EventDailyCountSummary;
import com.demo.eventcounter.dto.domain.DateHourEventCount;
import com.demo.eventcounter.dto.domain.EventCount;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Test {
    @org.junit.Test
    public void test(){
        List<EventDailyCountSummary> summaries = new ArrayList<EventDailyCountSummary>();
        //summaries=null;
//        summaries.add(new TestSummary("testurl", LocalDate.now(), "CLICK", 10, 0));
//        summaries.add(new TestSummary("testurl", LocalDate.now(), "VIEW", 12, 0));
//        summaries.add(new TestSummary("testurl", LocalDate.now(), "CLICK", 102, 1));
//        summaries.add(new TestSummary("testurl", LocalDate.now(), "VIEW", 122, 1));
        List<DateHourEventCount> counts = summaries.stream()
                .collect(Collectors.toMap(com.demo.eventcounter.dao.domain.EventDailyCountSummary::getHournum, s -> {
                    List<EventCount> list = new ArrayList<EventCount>();
                    list.add(new EventCount(s.getType(), s.getCount()));
                    return list;
                }, (v1, v2) -> {
                    v1.addAll(v2);
                    return v1;
                })).entrySet().stream()
                .map(s -> {
                    DateHourEventCount c = new DateHourEventCount();
                    c.setDatehour(s.getKey());
                    c.setEventCounts(s.getValue());
                    return c;
                }).collect(Collectors.toList());
        System.out.println(counts);
    }

    public class TestSummary implements EventDailyCountSummary{
        private String url;
        private LocalDate date;
        private String type;
        private Integer count;
        private Integer hourNum;

        public TestSummary(String url, LocalDate date, String type, Integer count, Integer hourNum) {
            this.url = url;
            this.date = date;
            this.type = type;
            this.count = count;
            this.hourNum = hourNum;
        }

        @Override
        public String getUrl() {
            return url;
        }

        @Override
        public LocalDate getDate() {
            return date;
        }

        @Override
        public String getType() {
            return type;
        }

        @Override
        public Integer getHournum() {
            return hourNum;
        }

        @Override
        public Integer getCount() {
            return count;
        }
    }
}
