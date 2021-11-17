FROM openjdk:11-jre-slim as event-generation-endpoint
COPY ./event-generation-endpoint/target/event-generation-endpoint-0.0.1-SNAPSHOT.jar \
/usr/local/lib/event-generation-endpoint.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/lib/event-generation-endpoint.jar"]

FROM openjdk:11-jre-slim as event-persistence-consumer
COPY ./event-persistence-consumer/target/event-persistence-consumer-0.0.1-SNAPSHOT.jar \
/usr/local/lib/event-persistence-consumer.jar
ENTRYPOINT ["java", "-jar", "/usr/local/lib/event-persistence-consumer.jar"]