--Testing UNOGAME DATABASE

--Insert into the database
INSERT INTO users (username, password)
VALUES
	('gs', aes_encrypt('e','key')),
	('d', aes_encrypt('w', 'key')),
	('g', aes_encrypt('d', 'key')),
	('c', aes_encrypt('h', 'key'));
	
INSERT INTO wins (username, wins)
VALUES
	('gs', 3),
	('d', 5),
	('g', 4),
	('c', 8);

