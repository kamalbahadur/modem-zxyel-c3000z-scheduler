CREATE TABLE schedule (
    day_of_the_week VARCHAR NOT NULL,
    hour_of_the_day INT NOT NULL,
    minute_of_the_hour INT NOT NULL,
    connect_to_wifi BOOL NOT NULL,
    PRIMARY KEY (day_of_the_week, hour_of_the_day, minute_of_the_hour)
);

INSERT INTO schedule VALUES ('MONDAY', 22, 30, 0);
INSERT INTO schedule VALUES ('MONDAY', 3, 0, 1);
INSERT INTO schedule VALUES ('TUESDAY', 22, 30, 0);
INSERT INTO schedule VALUES ('TUESDAY', 3, 0, 1);
INSERT INTO schedule VALUES ('WEDNESDAY', 22, 30, 0);
INSERT INTO schedule VALUES ('WEDNESDAY', 3, 0, 1);
INSERT INTO schedule VALUES ('THURSDAY', 22, 30, 0);
INSERT INTO schedule VALUES ('THURSDAY', 3, 0, 1);
INSERT INTO schedule VALUES ('FRIDAY', 23, 30, 0);
INSERT INTO schedule VALUES ('FRIDAY', 3, 0, 1);
INSERT INTO schedule VALUES ('SATURDAY', 23, 30, 0);
INSERT INTO schedule VALUES ('SATURDAY', 3, 0, 1);
INSERT INTO schedule VALUES ('SUNDAY', 22, 30, 0);
INSERT INTO schedule VALUES ('SUNDAY', 3, 0, 1);