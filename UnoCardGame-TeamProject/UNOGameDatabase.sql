-- UNO Game DATABASE
-- Drop all tables to prevent problems when creating them at the beginning
DROP TABLE wins;
DROP TABLE users;

--Create all tables to be used
CREATE TABLE users
(
	username VARCHAR(25),
	password VARBINARY(16)
);

CREATE TABLE wins
(
	username VARCHAR(25),
	wins INTEGER(3)
);

--Create the table constraints 
ALTER TABLE users
	ADD CONSTRAINT pk_users
	PRIMARY KEY(username);
	
ALTER TABLE wins
	ADD CONSTRAINT pk_wins
	PRIMARY KEY(username);

--Create the foreign key table constraints
ALTER TABLE wins
	ADD CONSTRAINT fk_wins
	FOREIGN KEY(username) REFERENCES users(username);