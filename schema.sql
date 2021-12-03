CREATE TABLE event (
	uuid VARCHAR PRIMARY KEY,
	type VARCHAR,
	ip VARCHAR,
	url VARCHAR,
	datetime TIMESTAMP
);

CREATE TABLE event_summary (
	url VARCHAR,
	datehour TIMESTAMP,
	type VARCHAR,
	count INTEGER,
	lastupdate TIMESTAMP,
	PRIMARY KEY (url, datehour, type)
);

CREATE TABLE url_event(
    url VARCHAR,
    type VARCHAR,
    PRIMARY KEY (url, type)
);

CREATE TABLE date_hour(
    hour_num INTEGER
);

INSERT INTO url_event (url, type) VALUES ('https://www.google.com', 'CLICK');
INSERT INTO url_event (url, type) VALUES ('https://www.google.com', 'VIEW');
INSERT INTO url_event (url, type) VALUES ('https://www.facebook.com', 'CLICK');
INSERT INTO url_event (url, type) VALUES ('https://www.facebook.com', 'VIEW');
INSERT INTO url_event (url, type) VALUES ('https://www.youtube.com', 'CLICK');
INSERT INTO url_event (url, type) VALUES ('https://www.youtube.com', 'VIEW');

INSERT INTO date_hour VALUES (0);
INSERT INTO date_hour VALUES (1);
INSERT INTO date_hour VALUES (2);
INSERT INTO date_hour VALUES (3);
INSERT INTO date_hour VALUES (4);
INSERT INTO date_hour VALUES (5);
INSERT INTO date_hour VALUES (6);
INSERT INTO date_hour VALUES (7);
INSERT INTO date_hour VALUES (8);
INSERT INTO date_hour VALUES (9);
INSERT INTO date_hour VALUES (10);
INSERT INTO date_hour VALUES (11);
INSERT INTO date_hour VALUES (12);
INSERT INTO date_hour VALUES (13);
INSERT INTO date_hour VALUES (14);
INSERT INTO date_hour VALUES (15);
INSERT INTO date_hour VALUES (16);
INSERT INTO date_hour VALUES (17);
INSERT INTO date_hour VALUES (18);
INSERT INTO date_hour VALUES (19);
INSERT INTO date_hour VALUES (20);
INSERT INTO date_hour VALUES (21);
INSERT INTO date_hour VALUES (22);
INSERT INTO date_hour VALUES (23);

