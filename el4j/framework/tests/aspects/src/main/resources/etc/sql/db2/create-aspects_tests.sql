-- Version: $Revision: 3920 $
-- URL: $URL: https://el4j.svn.sourceforge.net/svnroot/el4j/tags/el4j_1_7/el4j/applications/templates/common/keyword/src/main/resources/etc/sql/db2/create-keyword.sql $
-- Date: $Date: 2009-09-16 13:40:27 +0200 (Wed, 16 Sep 2009) $
-- Author: $Author: dajamesthomas $

CREATE TABLE aspects_tests (
  keyid                       INTEGER        NOT NULL 
    GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1)
    PRIMARY KEY,
  name                        VARCHAR(64)    UNIQUE NOT NULL,
  description                 VARCHAR(256),
  optimisticLockingVersion    INTEGER        NOT NULL
);