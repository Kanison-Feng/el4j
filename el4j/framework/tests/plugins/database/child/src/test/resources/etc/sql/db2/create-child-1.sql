CREATE TABLE TESTCHILDONE(
  name    VARCHAR(64)    UNIQUE NOT NULL
);

-- check dependencies
SELECT * FROM TESTPARENTB;