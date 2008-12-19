-- Oracle key ranges: 1 - 2000000000 remote, 2000000001 - 4000000001 local

CREATE SEQUENCE simpleperson_sequence INCREMENT BY 1 START WITH 2000000001 MINVALUE 2000000001 MAXVALUE 4000000000 ;
CREATE SEQUENCE person_sequence INCREMENT BY 1 START WITH 2000000001 MINVALUE 2000000001 MAXVALUE 4000000000 ;

CREATE TABLE SIMPLEPERSON (
    ID NUMBER(10) NOT NULL PRIMARY KEY,
    VERSION NUMBER(10) NOT NULL,
    NAME VARCHAR2(40) NOT NULL,
    EMAIL VARCHAR2(40) NOT NULL
);

CREATE TABLE PERSON (
    ID NUMBER (10) NOT NULL PRIMARY KEY,
    VERSION NUMBER(10) NOT NULL,
    NAME VARCHAR2(40) NOT NULL,
    PARENT NUMBER(10) REFERENCES PERSON(ID)
);

-- The offliner tables.

CREATE SEQUENCE keymap_sequence INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE properties_sequence INCREMENT BY 1 START WITH 1;

CREATE TABLE KEYMAP (
    ID NUMBER(10) NOT NULL PRIMARY KEY,   
    LOCALBASEVERSION VARCHAR2(128) NOT NULL,
    REMOTEBASEVERSION VARCHAR2(128) NOT NULL,
    DELETEVERSION NUMBER(10)NOT NULL,
    SYNCVERSION NUMBER(10),
    LOCALKEY VARCHAR2(128) NOT NULL,
    REMOTEKEY VARCHAR2(128) NOT NULL
);

CREATE TABLE OFFLINERPROPERTIES (
    ID NUMBER (10) NOT NULL PRIMARY KEY,
    PROPNAME VARCHAR2(30) NOT NULL UNIQUE,
    PROPVALUE VARCHAR2(40)
);
