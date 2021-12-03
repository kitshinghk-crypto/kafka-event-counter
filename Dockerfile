FROM openjdk:11-jre-slim as event-generation-endpoint
COPY ./event-generation-endpoint/target/event-generation-endpoint-0.0.1-SNAPSHOT.jar \
/usr/local/lib/event-generation-endpoint.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/lib/event-generation-endpoint.jar"]

FROM openjdk:11-jre-slim as event-persistence-consumer
COPY ./event-persistence-consumer/target/event-persistence-consumer-0.0.1-SNAPSHOT.jar \
/usr/local/lib/event-persistence-consumer.jar
ENTRYPOINT ["java", "-jar", "/usr/local/lib/event-persistence-consumer.jar"]

FROM openjdk:11-jre-slim as event-realtime-counter-consumer
COPY ./event-realtime-counter-consumer/target/event-realtime-counter-consumer-0.0.1-SNAPSHOT.jar \
/usr/local/lib/event-realtime-counter-consumer.jar
ENTRYPOINT ["java", "-jar", "/usr/local/lib/event-realtime-counter-consumer.jar"]

FROM openjdk:11-jre-slim as event-realtime-tracker
COPY ./event-realtime-tracker/target/event-realtime-tracker-0.0.1-SNAPSHOT.jar \
/usr/local/lib/event-realtime-tracker.jar
ENTRYPOINT ["java", "-jar", "/usr/local/lib/event-realtime-tracker.jar"]

FROM openjdk:11-jre-slim as event-data-aggregation
COPY ./event-data-aggregation/target/event-data-aggregation-0.0.1-SNAPSHOT.jar \
/usr/local/lib/event-data-aggregation.jar
ENTRYPOINT ["java", "-jar", "/usr/local/lib/event-data-aggregation.jar"]

FROM openjdk:11-jre-slim as event-statistics-endpoint
COPY ./event-statistics-endpoint/target/event-statistics-endpoint-0.0.1-SNAPSHOT.jar \
/usr/local/lib/event-statistics-endpoint.jar
ENTRYPOINT ["java", "-jar", "/usr/local/lib/event-statistics-endpoint.jar"]