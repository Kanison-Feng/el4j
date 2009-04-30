-- This file is read and executed by the test starter.

CREATE SEQUENCE simpleperson_sequence INCREMENT BY 1 START WITH 1 ;
CREATE SEQUENCE person_sequence INCREMENT BY 1 START WITH 1 ;

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