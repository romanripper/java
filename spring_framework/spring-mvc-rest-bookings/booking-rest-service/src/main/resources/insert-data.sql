INSERT INTO PUBLIC.USER(FIRST_NAME, LAST_NAME, LOGIN, PASSWORD, ENABLED) values('John', 'Smith', 'jsmith','$2a$10$4H/cQZWhKNICUkxl9cfVEu.Vwxzf9ycfjEOFtGvzYRWVBsCk58qeq', true);
INSERT INTO PUBLIC.USER(FIRST_NAME, LAST_NAME, LOGIN, PASSWORD, ENABLED) values('Jane','Doe','jdoe','$2a$10$xJSYkX8zHfHv8WJxtXjFA.KgVTv5Q1M/6xZ8kYfgEiIJbuw.sFH1u', true);
INSERT INTO PUBLIC.USER(FIRST_NAME, LAST_NAME, LOGIN, PASSWORD, ENABLED) values('Mary','Arnold','admin','$2a$10$n.6B6AWKA2YpmyO5oXXh7e4pLRs7F5.UUhXMZs26.Ry5oAOI87BTW', true);

INSERT INTO PUBLIC.USERS_ROLES(USER_ID, ROLE) values((SELECT u.ID FROM PUBLIC.USER AS u WHERE u.login = 'jsmith'), 'ROLE_EMPLOYEE');
INSERT INTO PUBLIC.USERS_ROLES(USER_ID, ROLE) values((SELECT u.ID FROM PUBLIC.USER AS u WHERE u.login = 'jdoe'), 'ROLE_EMPLOYEE');
INSERT INTO PUBLIC.USERS_ROLES(USER_ID, ROLE) values((SELECT u.ID FROM PUBLIC.USER AS u WHERE u.login = 'admin'), 'ROLE_ADMIN');


INSERT INTO PUBLIC.ROOM(HAS_PROJECTOR, LOCATION_DESCRIPTION, NUMBER_OF_SEATS, PHONE_NUBMER, ROOM_NAME)
VALUES (false, '2nd floor', 7, null, 'Small Room');
INSERT INTO PUBLIC.ROOM(HAS_PROJECTOR, LOCATION_DESCRIPTION, NUMBER_OF_SEATS, PHONE_NUBMER, ROOM_NAME)
VALUES (true, '1st floor', 10, '22-22-22-22', 'Large Room');
INSERT INTO PUBLIC.ROOM(HAS_PROJECTOR, LOCATION_DESCRIPTION, NUMBER_OF_SEATS, PHONE_NUBMER, ROOM_NAME)
VALUES (true, '1st floor', 6, null, 'Medium Room');

INSERT INTO PUBLIC.BOOKING(DATE_FROM, DATE_TO, ROOM_ID, USER_ID)
VALUES ('2019-10-19 12:00:00', '2019-10-19 13:00:00.0',
(SELECT r.ID FROM PUBLIC.ROOM AS r WHERE r.room_name = 'Small Room'),
(SELECT u.ID FROM PUBLIC.USER AS u WHERE u.login = 'jsmith'));

INSERT INTO PUBLIC.BOOKING(DATE_FROM, DATE_TO, ROOM_ID, USER_ID)
VALUES ('2019-10-21 12:00:00', '2019-10-23 16:30:00.0',
(SELECT r.ID FROM PUBLIC.ROOM AS r WHERE r.room_name = 'Large Room'),
(SELECT u.ID FROM PUBLIC.USER AS u WHERE u.login = 'jsmith'));

--INSERT INTO PUBLIC.BOOKING(DATE_FROM, DATE_TO, ROOM_ID, USER_ID)
--VALUES ('2019-10-19 12:00:00', '2019-10-19 17:00:00.0',
--(SELECT r.ID FROM PUBLIC.ROOM AS r WHERE r.room_name = 'Small Room'),
--(SELECT u.ID FROM PUBLIC.USER AS u WHERE u.login = 'jsmith'));
--
--INSERT INTO PUBLIC.BOOKING(DATE_FROM, DATE_TO, ROOM_ID, USER_ID)
--VALUES ('2019-10-19 12:00:00', '2019-10-19 13:00:00.0',
--(SELECT r.ID FROM PUBLIC.ROOM AS r WHERE r.room_name = 'Small Room'),
--(SELECT u.ID FROM PUBLIC.USER AS u WHERE u.login = 'jsmith'));
