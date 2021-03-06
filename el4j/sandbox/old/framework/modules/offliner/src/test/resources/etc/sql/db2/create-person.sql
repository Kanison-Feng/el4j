-- This file is read and executed by the test starter.

CREATE TABLE SIMPLEPERSON (
    ID BIGINT NOT NULL PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY 
		(START WITH 1, INCREMENT BY 1),
    VERSION BIGINT NOT NULL,
    NAME VARCHAR(40) NOT NULL,
    EMAIL VARCHAR(40) NOT NULL
);

CREATE TABLE PERSON (
	ID BIGINT NOT NULL PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY 
		(START WITH 1, INCREMENT BY 1),
    VERSION BIGINT NOT NULL,
	NAME VARCHAR(40) NOT NULL,
	PARENT BIGINT REFERENCES PERSON(ID)
);
