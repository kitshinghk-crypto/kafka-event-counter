# Kafka-event-counter
This repository contains a system which counts the event using Java, Spring Boot, Kafka, Redis and Postgresql. The project is built using Docker. The system has a micoservices architecture. It consists of multiple modules. The following diagram shows the overview of the system structure:

![System diagram](https://github.com/kitshinghk-crypto/kafka-event-counter/blob/main/Untitled%20Diagram.jpg?raw=true)

##  Event Producer Endpoint
[Event Producer Endpoint](event-generation-endpoint) module is a REST API endpoint receiving event requests 
from end users. A event can be created by the following example API call:
```
bash % curl -v --request PUT \
  --url http://localhost/event_generate/ \
  --header 'Content-Type: application/json' \
  --data '{
        "uuid":"c7a84fa3-bd42-4c6c-acbd-50506325ac1",
        "url":"https://www.example.net",
        "type": "CLICK",
        "ip":"323.3253.34.999"
}'
*   Trying ::1:80...
* Connected to localhost (::1) port 80 (#0)
> PUT /event_generate/ HTTP/1.1
> Host: localhost
> User-Agent: curl/7.77.0
> Accept: */*
> Content-Type: application/json
> Content-Length: 126
> 
* Mark bundle as not supporting multiuse
< HTTP/1.1 200 
< Server: nginx/1.21.4
< Date: Fri, 03 Dec 2021 20:21:13 GMT
< Content-Type: text/plain;charset=UTF-8
< Content-Length: 2
< Connection: keep-alive
< 
* Connection #0 to host localhost left intact
OK%                                
```
After receiving the request, the module insert the event in the Kafka queue for further process and the API return "OK" 
if the request is handled successfully.

##  Event Persistance Consumer
[Event Persistance Consumer](event-persistence-consumer) consumes the event messages in the Kafka queue and insert the 
event in the Postgre DB. The module belongs to the Kafka consumer group, "group_persistence".
The following shows the 'event' table schema in Postgre DB. 

```
event_counter=# select * from event;
                 uuid                 | type  |       ip        |           url            |          datetime          
--------------------------------------+-------+-----------------+--------------------------+----------------------------
 e2a562b3-afe3-40f4-adc1-76c3ef9a0143 | VIEW  | 323.3253.34.999 | https://www.youtube.com  | 2021-12-03 19:47:19.879263
 22a4ab42-cd7c-4a8f-aa94-0f5ece5d4aff | VIEW  | 323.3253.34.999 | https://www.facebook.com | 2021-12-03 19:47:23.317557
 6503dc8d-2ef2-476c-b860-eac59799a706 | CLICK | 323.3253.34.999 | https://www.facebook.com | 2021-12-03 19:47:26.358856
 61b8db54-9e1a-4941-a75b-ea4fa168e607 | VIEW  | 323.3253.34.999 | https://www.youtube.com  | 2021-12-03 19:47:30.426328
```
## Event real-time counter consumer
[Event real-time counter consumer](event-realtime-counter-consumer) consumes the event messages in the Kafka queue and 
update the atomic integer counter in Redis. The module belongs to the Kafka consumer group, "group_realtimecounter". The atomic counter uses the url and the event type as the key, so that for each event type of a url is counted separately. 

## Real-time Event Tracker Endpoint
[Real-time Event Tracker Endpoint](event-realtime-tracker) provides REST API endpoint for tracking the real-time counter
for the event occured for each url. The counter is retrived from Redis cacahe. The following shows an example of the API call and its corresponding response.
```
bash% curl -v --request POST \
  --url http://localhost/event_track/ \
  --header 'Content-Type: application/json' \
  --data '{
        "url":"https://www.facebook.com"
}
'
Note: Unnecessary use of -X or --request, POST is already inferred.
*   Trying ::1:80...
* Connected to localhost (::1) port 80 (#0)
> POST /event_track/ HTTP/1.1
> Host: localhost
> User-Agent: curl/7.77.0
> Accept: */*
> Content-Type: application/json
> Content-Length: 38
> 
* Mark bundle as not supporting multiuse
< HTTP/1.1 200 
< Server: nginx/1.21.4
< Date: Fri, 03 Dec 2021 20:19:39 GMT
< Content-Type: application/json
< Transfer-Encoding: chunked
< Connection: keep-alive
< 
* Connection #0 to host localhost left intact
{"url":"https://www.facebook.com","events":[{"type":"CLICK","count":151},{"type":"VIEW","count":186}]}
```
## Data Aggregation Worker
[Data Aggregation Worker](event-data-aggregation) aggregates the event data in Postgresql DB into a summary table. The worker aggregate
the count every 5 minutes by each url, hour and event type. The following table shows an example of the summary table.
```
event_counter=# select * from event_summary;
           url            |      datehour       | type  | count |         lastupdate         
--------------------------+---------------------+-------+-------+----------------------------
 https://www.facebook.com | 2021-12-03 19:00:00 | CLICK |    47 | 2021-12-03 19:59:59.674667
 https://www.facebook.com | 2021-12-03 19:00:00 | VIEW  |    43 | 2021-12-03 19:59:59.674667
 https://www.google.com   | 2021-12-03 19:00:00 | CLICK |    51 | 2021-12-03 19:59:59.674667
 https://www.google.com   | 2021-12-03 19:00:00 | VIEW  |    40 | 2021-12-03 19:59:59.674667
 https://www.youtube.com  | 2021-12-03 19:00:00 | CLICK |    37 | 2021-12-03 19:59:59.674667
 https://www.youtube.com  | 2021-12-03 19:00:00 | VIEW  |    43 | 2021-12-03 19:59:59.674667
 https://www.example.net  | 2021-12-03 20:00:00 | CLICK |     2 | 2021-12-03 20:19:59.797136
 https://www.facebook.com | 2021-12-03 20:00:00 | CLICK |   104 | 2021-12-03 20:19:59.797136
 https://www.facebook.com | 2021-12-03 20:00:00 | VIEW  |   148 | 2021-12-03 20:19:59.797136
 https://www.google.com   | 2021-12-03 20:00:00 | CLICK |   135 | 2021-12-03 20:19:59.797136
 https://www.google.com   | 2021-12-03 20:00:00 | VIEW  |   139 | 2021-12-03 20:19:59.797136
 https://www.youtube.com  | 2021-12-03 20:00:00 | CLICK |   136 | 2021-12-03 20:19:59.797136
 https://www.youtube.com  | 2021-12-03 20:00:00 | VIEW  |   128 | 2021-12-03 20:19:59.797136
(13 rows)
```

## Event Statistics Endpoint
[Event Statistics Endpoint](event-statistics-endpoint) provides a REST API endpoint to show the daily event statistics.
The endpoint returns the count of each event type of each hour for a specified date and url. The data is retrived from the summary table of postgresql DB. The following shows an example API call.

```
% curl -v --request POST \
  --url http://localhost/event_report/ \
  --header 'Content-Type: application/json' \
  --data '{
        "url":"https://www.facebook.com",
        "date":"2021-12-03"
}'
Note: Unnecessary use of -X or --request, POST is already inferred.
*   Trying ::1:80...
* Connected to localhost (::1) port 80 (#0)
> POST /event_report/ HTTP/1.1
> Host: localhost
> User-Agent: curl/7.77.0
> Accept: */*
> Content-Type: application/json
> Content-Length: 59
> 
* Mark bundle as not supporting multiuse
< HTTP/1.1 200 
< Server: nginx/1.21.4
< Date: Fri, 03 Dec 2021 20:29:23 GMT
< Content-Type: application/json
< Transfer-Encoding: chunked
< Connection: keep-alive
< 
* Connection #0 to host localhost left intact
{"url":"https://www.facebook.com","date":"2021-12-03","dateHourEventCounts":[{"datehour":0,"eventCounts":[{"type":"CLICK","count":0},{"type":"VIEW","count":0}]},{"datehour":1,"eventCounts":[{"type":"CLICK","count":0},{"type":"VIEW","count":0}]},{"datehour":2,"eventCounts":[{"type":"CLICK","count":0},{"type":"VIEW","count":0}]},{"datehour":3,"eventCounts":[{"type":"CLICK","count":0},{"type":"VIEW","count":0}]},{"datehour":4,"eventCounts":[{"type":"CLICK","count":0},{"type":"VIEW","count":0}]},{"datehour":5,"eventCounts":[{"type":"CLICK","count":0},{"type":"VIEW","count":0}]},{"datehour":6,"eventCounts":[{"type":"CLICK","count":0},{"type":"VIEW","count":0}]},{"datehour":7,"eventCounts":[{"type":"CLICK","count":0},{"type":"VIEW","count":0}]},{"datehour":8,"eventCounts":[{"type":"CLICK","count":0},{"type":"VIEW","count":0}]},{"datehour":9,"eventCounts":[{"type":"CLICK","count":0},{"type":"VIEW","count":0}]},{"datehour":10,"eventCounts":[{"type":"CLICK","count":0},{"type":"VIEW","count":0}]},{"datehour":11,"eventCounts":[{"type":"CLICK","count":0},{"type":"VIEW","count":0}]},{"datehour":12,"eventCounts":[{"type":"CLICK","count":0},{"type":"VIEW","count":0}]},{"datehour":13,"eventCounts":[{"type":"CLICK","count":0},{"type":"VIEW","count":0}]},{"datehour":14,"eventCounts":[{"type":"CLICK","count":0},{"type":"VIEW","count":0}]},{"datehour":15,"eventCounts":[{"type":"CLICK","count":0},{"type":"VIEW","count":0}]},{"datehour":16,"eventCounts":[{"type":"CLICK","count":0},{"type":"VIEW","count":0}]},{"datehour":17,"eventCounts":[{"type":"CLICK","count":0},{"type":"VIEW","count":0}]},{"datehour":18,"eventCounts":[{"type":"CLICK","count":0},{"type":"VIEW","count":0}]},{"datehour":19,"eventCounts":[{"type":"CLICK","count":47},{"type":"VIEW","count":43}]},{"datehour":20,"eventCounts":[{"type":"CLICK","count":129},{"type":"VIEW","count":187}]},{"datehour":21,"eventCounts":[{"type":"CLICK","count":0},{"type":"VIEW","count":0}]},{"datehour":22,"eventCounts":[{"type":"CLICK","count":0},{"type":"VIEW","count":0}]},{"datehour":23,"eventCounts":[{"type":"CLICK","count":0},{"type":"VIEW","count":0}]}]}% 
```
