CREATE TABLE TESTCHILDTWO(
  name    VARCHAR(64)    UNIQUE NOT NULL
);

-- check dependencies
SELECT * FROM TESTCHILDONE;