
CREATE SEQUENCE brain_sequence    INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE person_sequence   INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE tooth_sequence   INCREMENT BY 1 START WITH 1;

CREATE TABLE brain (
  keyId                     INTEGER        	NOT NULL
    PRIMARY KEY,
  optimisticLockingVersion  INTEGER        	NOT NULL,
  iq						INTEGER			NOT NULL
);

CREATE TABLE person (
   keyId                     INTEGER        	NOT NULL
    PRIMARY KEY,
   optimisticLockingVersion  INTEGER        	NOT NULL,
   legalStatus				 INTEGER,
   name						 VARCHAR(255),
   brain_key				 INTEGER
	NOT NULL	REFERENCES brain(keyId)
);

CREATE TABLE friends (
   person_keyid				 INTEGER
	NOT NULL	REFERENCES person(keyId),
   friends_keyid 			 INTEGER
	NOT NULL	REFERENCES person(keyId),
   PRIMARY KEY (person_keyid, friends_keyid)
);


CREATE TABLE tooth (
   keyId                     INTEGER        	NOT NULL
    PRIMARY KEY,
   optimisticLockingVersion  INTEGER        	NOT NULL,
   age		                 INTEGER        	NOT NULL,
   owner_keyid 				 INTEGER
	NOT NULL	REFERENCES person(keyId)
);

CREATE TABLE person_tooth
(
   person_keyid			 INTEGER
	NOT NULL	REFERENCES person(keyId),
   teeth_keyid			 INTEGER
	NOT NULL	REFERENCES tooth(keyId),
   PRIMARY KEY (person_keyid, teeth_keyid)
);

