--Testing UNOGAME DATABASE

--Insert into the database
INSERT INTO users (username, password)
VALUES
	('gs', aes_encrypt('e','key')),
	('d', aes_encrypt('w', 'key')),
	('g', aes_encrypt('d', 'key')),
	('c', aes_encrypt('h', 'key'));

