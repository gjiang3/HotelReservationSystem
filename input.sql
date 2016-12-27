DROP DATABASE IF EXISTS hoteldb;
CREATE DATABASE hoteldb;
USE hoteldb;

DROP TABLE IF EXISTS user;
CREATE TABLE user (
	username VARCHAR(12) NOT NULL, 
	password VARCHAR(20) NOT NULL, 
	firstName VARCHAR(15) NOT NULL, 
	lastName VARCHAR(15) NOT NULL,
	age INT NOT NULL,
	gender ENUM ('M', 'F'),
    userRole ENUM ('Customer', 'Manager', 'Room Attendant'),
	question VARCHAR(50) NOT NULL,
	answer VARCHAR(30) NOT NULL,
	PRIMARY KEY (username)
);

DROP TABLE IF EXISTS room;
CREATE TABLE room (
	roomId INT(10) NOT NULL AUTO_INCREMENT,
	costPerNight DOUBLE(10,2) NOT NULL,
	roomType VARCHAR(20) NOT NULL,
	PRIMARY KEY (roomId)
);

DROP TABLE IF EXISTS reservation;
CREATE TABLE reservation (
	reservationId INT(10) NOT NULL AUTO_INCREMENT,
	roomId INT(10) NOT NULL,
	customer VARCHAR(12) NOT NULL,
	startDate DATE NOT NULL,
	endDate DATE NOT NULL,
	numOfDays INT(10),
	totalCost DOUBLE(10,2),
	canceled BOOLEAN NOT NULL DEFAULT FALSE,
    updatedOn TIMESTAMP NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp,
	PRIMARY KEY (reservationId),
	FOREIGN KEY (roomId) references room (roomId) ON DELETE CASCADE,
	FOREIGN KEY (customer) references user (username) ON DELETE CASCADE
);

DROP TABLE IF EXISTS archive_reservation;
CREATE TABLE archive_reservation (
	reservationId INT(10) NOT NULL,
	roomId INT(10) NOT NULL,
	customer VARCHAR(12) NOT NULL,
	startDate DATE NOT NULL,
	endDate DATE NOT NULL,
	numOfDays INT(10) NOT NULL,
	totalCost DOUBLE(10,2),
	canceled BOOLEAN NOT NULL DEFAULT FALSE,
    updatedOn TIMESTAMP NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp,
	PRIMARY KEY (reservationId),
	FOREIGN KEY (roomId) references room (roomId) ON DELETE CASCADE,
	FOREIGN KEY (customer) references user (username) ON DELETE CASCADE
);

DROP TABLE IF EXISTS roomservice;
CREATE TABLE roomservice (
	taskId INT(10) NOT NULL AUTO_INCREMENT,
    username VARCHAR(12) NOT NULL,
	task VARCHAR(250) NOT NULL,
    completedBy VARCHAR(12),
    updatedOn TIMESTAMP NOT NULL DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (taskId),
    FOREIGN KEY (completedBy) references user (username) ON DELETE CASCADE,
	FOREIGN KEY (username) references user (username) ON DELETE CASCADE
);

DROP TABLE IF EXISTS archive_roomservice;
CREATE TABLE archive_roomservice (
	taskId INT(10) NOT NULL AUTO_INCREMENT,
    username VARCHAR(12) NOT NULL,
	task VARCHAR(250) NOT NULL,
    completedBy VARCHAR(12),
    updatedOn TIMESTAMP NOT NULL DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (taskId),
    FOREIGN KEY (completedBy) references user (username) ON DELETE CASCADE,
	FOREIGN KEY (username) references user (username) ON DELETE CASCADE
);

DROP TABLE IF EXISTS complaint;
CREATE TABLE complaint (
	complaintId INT(10) NOT NULL AUTO_INCREMENT,
	customer VARCHAR(12) NOT NULL,
	complaint VARCHAR(100) NOT NULL,
	time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	resolvedBy VARCHAR(12),
	solution VARCHAR(100),
    updatedOn TIMESTAMP NOT NULL DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (complaintId),
	FOREIGN KEY (customer) references user (username) ON DELETE CASCADE,
	FOREIGN KEY (resolvedBy) references user (username) ON DELETE CASCADE
);


DROP TABLE IF EXISTS archive_complaint;
CREATE TABLE archive_complaint (
	complaintId INT(10) NOT NULL,
	customer VARCHAR(12) NOT NULL,
	complaint VARCHAR(100) NOT NULL,
	time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	resolvedBy VARCHAR(12),
	solution VARCHAR(100),
    updatedOn TIMESTAMP NOT NULL DEFAULT current_timestamp  ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (complaintId),
	FOREIGN KEY (customer) references user (username) ON DELETE CASCADE,
	FOREIGN KEY (resolvedBy) references user (username) ON DELETE CASCADE
);

DROP TABLE IF EXISTS rating;
CREATE TABLE rating (
	ratingId INT(10) NOT NULL AUTO_INCREMENT,
	customer VARCHAR(12) NOT NULL,
	rating INT NOT NULL,
	PRIMARY KEY (ratingId),
	FOREIGN KEY (customer) references user (username) ON DELETE CASCADE
);


DROP TABLE IF EXISTS complaint;
CREATE TABLE complaint (
	complaintId INT(10) NOT NULL AUTO_INCREMENT,
	customer VARCHAR(12) NOT NULL,
	complaint VARCHAR(100) NOT NULL,
	time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	resolvedBy VARCHAR(12),
	solution VARCHAR(100),
    updatedOn TIMESTAMP NOT NULL DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (complaintId),
	FOREIGN KEY (customer) references user (username) ON DELETE CASCADE,
	FOREIGN KEY (resolvedBy) references user (username) ON DELETE CASCADE
);

DROP TABLE IF EXISTS archive_complaint;
CREATE TABLE archive_complaint (
	complaintId INT(10) NOT NULL,
	customer VARCHAR(12) NOT NULL,
	complaint VARCHAR(100) NOT NULL,
	time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	resolvedBy VARCHAR(12),
	solution VARCHAR(100),
    updatedOn TIMESTAMP NOT NULL DEFAULT current_timestamp  ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (complaintId),
	FOREIGN KEY (customer) references user (username) ON DELETE CASCADE,
	FOREIGN KEY (resolvedBy) references user (username) ON DELETE CASCADE
);

DROP TABLE IF EXISTS rating;
CREATE TABLE rating (
	ratingId INT(10) NOT NULL AUTO_INCREMENT,
	customer VARCHAR(12) NOT NULL,
	rating INT NOT NULL,
	PRIMARY KEY (ratingId),
	FOREIGN KEY (customer) references user (username) ON DELETE CASCADE
);

drop trigger if exists defaultRating;
DELIMITER %%
create trigger defaultRating
after insert on user
for each row
begin
	if new.userRole = 'Customer' then
		insert into rating (customer, rating) value (new.username, 5);
    end if;
end;%%
DELIMITER ;

drop trigger if exists calc_days_insert;
DELIMITER %%
create trigger calc_days_insert
before insert on reservation
for each row
begin
	if (datediff(new.endDate, new.startDate) > 60)
	then signal sqlstate '10002' set message_text = 'Reservation cannot be greater than 60 days';	
    else
	set new.numOfDays = datediff(new.endDate, new.startDate);
    end if;
    
    if (select distinct room.roomId 
			from room left outer join reservation on room.roomId = reservation.roomId 
			where (new.startdate = reservation.startdate 
            or new.startdate = reservation.enddate
            or new.enddate = reservation.startdate
            or new.enddate = reservation.enddate
            or (reservation.startdate < new.enddate and reservation.enddate > new.startdate)
            or (new.startdate < reservation.startdate and new.enddate > reservation.startdate)
            or (new.startdate < reservation.enddate and new.enddate > reservation.enddate)) and new.roomid = reservation.roomid)
	then signal sqlstate '10002' set message_text = 'Room is taken';
    end if;
    
	set new.totalCost = (select costPerNight from room where roomId = new.roomId) * new.numOfDays;
end;
%%
DELIMITER ;

DROP PROCEDURE IF EXISTS archiveAll;
DELIMITER %%
CREATE PROCEDURE archiveAll
(IN cutoffDate TIMESTAMP)
LANGUAGE SQL
DETERMINISTIC
COMMENT 'SP TO HANDLE ARCHIVE'
BEGIN
	-- HANDLER FOR ANY SQL EXCEPTION
	DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;

	-- START THE TRANSACTION
	START TRANSACTION;
		-- ARCHIVE RESERVATION TABLE
		INSERT INTO archive_reservation(reservationId,roomId,customer,startDate,endDate,numOfDays,
		                                totalCost,canceled,updatedOn)
		SELECT reservationId,roomId,customer,startDate,endDate,numOfDays,totalCost,canceled,updatedOn
		FROM reservation
		WHERE DATE(updatedOn) <= cutoffDate;

		-- ARCHIVE ROOMSERVICE TABLE
		INSERT INTO archive_roomservice(taskId,username,task,completedBy,updatedOn)
		SELECT taskId,username,task,completedBy,updatedOn
		FROM roomservice
		WHERE DATE(updatedOn) <= cutoffDate;

		-- ARCHIVE COMPLAINT TABLE
		INSERT INTO archive_complaint(complaintId,customer,complaint,time,resolvedBy,solution,updatedOn)
		SELECT complaintId,customer,complaint,time,resolvedBy,solution,updatedOn
		FROM complaint
		WHERE DATE(updatedOn) <= cutoffDate;

		-- DELETE OLD ROWS FROM RESERVATION, ROOMSERVICE, AND COMPLAINT TABLES
		DELETE FROM RESERVATION WHERE DATE(updatedOn) <= cutoffDate;
		DELETE FROM ROOMSERVICE WHERE DATE(updatedOn) <= cutoffDate;
		DELETE FROM COMPLAINT WHERE DATE(updatedOn) <= cutoffDate;
	COMMIT;
END;
%%
DELIMITER ;

insert into room (costPerNight, roomType)
values (40, "Single Room"),(40, "Single Room"),(40, "Single Room"),
(40, "Single Room"),(40, "Single Room"),(40, "Single Room"),(40, "Single Room"),
(40, "Single Room"),(40, "Single Room"),(40, "Single Room"),(40, "Single Room"),
(60, "Double Room"),(60, "Double Room"), (60, "Double Room"),(60, "Double Room"),
(60, "Double Room"),(60, "Double Room"), (60, "Double Room"),(60, "Double Room"),
(60, "Double Room"),(60, "Double Room"), (60, "Double Room"),(60, "Double Room"),
(80, "Twin Room"),(80, "Twin Room"), (80, "Twin Room"),(80, "Twin Room"),
(80, "Twin Room"),(80, "Twin Room"), (80, "Twin Room"),(80, "Twin Room"), 
(80, "Twin Room"),(80, "Twin Room"), (80, "Twin Room"),(80, "Twin Room"),    
(100, "Suite Room"),(100, "Suite Room"), (100, "Suite Room"),(100, "Suite Room"),
(100, "Suite Room"),(100, "Suite Room"), (100, "Suite Room"),(100, "Suite Room"),
(100, "Suite Room"),(100, "Suite Room"), (100, "Suite Room"),(100, "Suite Room");

insert into user(username, password, firstName, lastName, userRole, age, gender, question, answer) values 
('admin', 'admin', 'Bill', 'Gates', 'Manager', 61, 'M', 'Who is the richest guy in the world?', 'Me');

insert into user(username, password, firstName, lastName, userRole, age, gender, question, answer) values 
('customer1', '123', 'test', 'test', 'Customer', 21, 'M', 'Hello', 'Hello');

insert into user(username, password, firstName, lastName, userRole, age, gender, question, answer) values 
('attendant1', '123', 'test', 'test', 'Room Attendant', 21, 'M', 'Hello', 'Hello');