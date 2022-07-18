INSERT INTO ROLES (name) VALUES ('ROLE_ADMIN');
INSERT INTO ROLES (name) VALUES ('ROLE_SALTERSKI_RADNIK');
INSERT INTO ROLES (name) VALUES ('ROLE_UPRAVNIK');
INSERT INTO ROLES (name) VALUES ('ROLE_POSTAR');
INSERT INTO ROLES (name) VALUES ('ROLE_OBRACUNSKI_RADNIK');

INSERT INTO USERS(id, deleted, email, enabled, last_password_reset_date, name, password, phone_number, points, surname, role_id, verification_code)
	VALUES (1, false, 'stojic.kris@gmail.com', true, null,  'Kristina', 'kris', '0643515864',1510, 'Stojic', 1, null);


