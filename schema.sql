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