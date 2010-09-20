CREATE TABLE brain (
  keyId                     INTEGER        	NOT NULL
   PRIMARY KEY AUTO_INCREMENT,
  optimisticLockingVersion  INTEGER        	NOT NULL,
  iq						INTEGER			NOT NULL
);

CREATE TABLE person (
   keyId                     INTEGER        	NOT NULL
    PRIMARY KEY AUTO_INCREMENT,
   optimisticLockingVersion  INTEGER        	NOT NULL,
   legalStatus				 INTEGER,
   name						 VARCHAR(255),
   brain_key				 INTEGER
	NOT NULL	REFERENCES brain(keyId),
   bestfriend_key            INTEGER
    REFERENCES person(keyId)
);

CREATE TABLE friends (
   friends_key               INTEGER        	NOT NULL,
   person_keyid				 INTEGER
	NOT NULL	REFERENCES person(keyId),
   friends_keyid 			 INTEGER
	NOT NULL	REFERENCES person(keyId),
   PRIMARY KEY (person_keyid, friends_key)
);


CREATE TABLE tooth (
   keyId                     INTEGER        	NOT NULL
    PRIMARY KEY AUTO_INCREMENT,
   optimisticLockingVersion  INTEGER        	NOT NULL,
   age		                 INTEGER        	NOT NULL,
   owner_keyid 				 INTEGER
	NOT NULL	REFERENCES person(keyId)
);

