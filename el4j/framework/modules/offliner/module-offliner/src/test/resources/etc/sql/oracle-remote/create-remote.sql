-- Create script for remote schema.
-- This file is read and executed by the test starter.

-- USE REMOTE;

-- Oracle key ranges: 1 - 2000000000 remote, 2000000001 - 4000000001 local

CREATE SEQUENCE simpleperson_sequence INCREMENT BY 1 START WITH 1 MINVALUE 1 MAXVALUE 2000000000 ;
CREATE SEQUENCE person_sequence INCREMENT BY 1 START WITH 1 MINVALUE 1 MAXVALUE 2000000000 ;

CREATE TABLE SIMPLEPERSON (
    ID NUMBER(10) NOT NULL PRIMARY KEY,
    VERSION NUMBER(10) NOT NULL,
    NAME VARCHAR2(40) NOT NULL,
    EMAIL VARCHAR2(40) NOT NULL
);

CREATE TABLE PERSON (
    ID NUMBER(10) NOT NULL PRIMARY KEY,
    VERSION NUMBER(10) NOT NULL,
    NAME VARCHAR2(40) NOT NULL,
    PARENT NUMBER(10) REFERENCES PERSON(ID)
);
