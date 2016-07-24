# DC schema

# --- !Ups

CREATE TABLE MOVIE (
  ID         INTEGER      NOT NULL AUTO_INCREMENT PRIMARY KEY,
  TITLE      VARCHAR(255) NOT NULL,
  RELEASED   TIMESTAMP,
  RUNTIME    INTEGER,
  GENRE      VARCHAR(255),
  COUNTRY    VARCHAR(255),
  DIRECTOR   VARCHAR(255),
  SYNOPSIS   TEXT,
  POSTER     VARCHAR(255),
  IMDBID     VARCHAR(255),
  IMDBRATING DECIMAL,
  TOMATOESID VARCHAR(255),
  DETAILS    INTEGER
);

CREATE TABLE CINEMA (
  ID       INTEGER      NOT NULL AUTO_INCREMENT PRIMARY KEY,
  NAME     VARCHAR(255) NOT NULL,
  URL      VARCHAR(255),
  TEL      VARCHAR(255),
  EMAIL    VARCHAR(255),
  LOCATION INTEGER
);

CREATE TABLE LOCATION (
  ID      INTEGER      NOT NULL AUTO_INCREMENT PRIMARY KEY,
  NAME    VARCHAR(255) NOT NULL,
  CINEMA  INTEGER      NOT NULL,
  CITY    VARCHAR(255) NOT NULL,
  POSTAL  VARCHAR(255) NOT NULL,
  STREET  VARCHAR(255) NOT NULL,
  ADDRESS VARCHAR(255) NOT NULL,
  FOREIGN KEY (CINEMA) REFERENCES CINEMA (ID)
);

CREATE TABLE SHOWING (
  ID       INTEGER   NOT NULL AUTO_INCREMENT PRIMARY KEY,
  MOVIE    INTEGER   NOT NULL,
  LOCATION INTEGER   NOT NULL,
  START    TIMESTAMP NOT NULL,
  END      TIMESTAMP,
  FOREIGN KEY (MOVIE) REFERENCES MOVIE (ID),
  FOREIGN KEY (LOCATION) REFERENCES LOCATION (ID)
);


INSERT INTO CINEMA (name) VALUES ('cinema1');
INSERT INTO MOVIE (title) VALUES ('title1');
INSERT INTO LOCATION VALUES (1, 'name1', 1, 'city', 'postal', 'street', 'address');
INSERT INTO SHOWING VALUES (1, 1, 1, '2012-06-18 10:34:09', '2012-06-18 10:34:09');

# --- !Downs

DROP TABLE SHOWING;
DROP TABLE MOVIE;
DROP TABLE CINEMA;
DROP TABLE LOCATION;
