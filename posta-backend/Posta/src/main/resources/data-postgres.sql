INSERT INTO ROLES (name) VALUES ('ROLE_ADMIN');
INSERT INTO ROLES (name) VALUES ('ROLE_COUNTER_WORKER');
INSERT INTO ROLES (name) VALUES ('ROLE_MANAGER');
INSERT INTO ROLES (name) VALUES ('ROLE_POSTMAN');
INSERT INTO ROLES (name) VALUES ('ROLE_ACCOUNTING_WORKER');

INSERT INTO USERS(id, deleted, email, enabled, last_password_reset_date, name, password, phone_number, surname, role_id, verification_code)
	VALUES (1236, false, 'stojic.kris@gmail.com', true, null,  'Kristina', 'kris', '0643515864', 'Stojic', 1, null);
INSERT INTO USERS(id, deleted, email, enabled, last_password_reset_date, name, password, phone_number, surname, role_id, verification_code)
	VALUES (1237, false, 'nikolastojic@gmail.com', true, null,  'Nikola', 'nikola', '06536526541', 'Stojic', 3, null);
INSERT INTO USERS(id, deleted, email, enabled, last_password_reset_date, name, password, phone_number, surname, role_id, verification_code)
	VALUES (1238, false, 'markomarko@gmail.com', true, null,  'Marko', 'marko', '0636523562', 'Markovic', 3, null);


INSERT INTO public.country(id, country_name) VALUES (100, 'Srbija');

INSERT INTO public.city(id, city_name, postal_code, country_id) VALUES (150, 'Novi Sad', 21000, 100);
INSERT INTO public.city(id, city_name, postal_code, country_id) VALUES (151, 'Subotica', 24000, 100);


INSERT INTO public.address(id, latitude, longitude, street, street_number, city_id) VALUES (155, 15.265, 45.235, 'Svetosavska', 10, 150);
INSERT INTO public.address(id, latitude, longitude, street, street_number, city_id) VALUES (156, 17.615, 47.354, 'Balzakova', 11, 151);


INSERT INTO public.post_office(id, employee_number, phone_number, address_id, deleted) VALUES (33, 8, 062352654, 155, false);
INSERT INTO public.post_office(id, employee_number, phone_number, address_id, deleted) VALUES (34, 11, 0653562545, 156, false);


INSERT INTO public.worker(id, post_office_id) VALUES (1237, 33);
INSERT INTO public.worker(id, post_office_id) VALUES (1238, 34);

INSERT INTO public.manager(id) VALUES (1237);
INSERT INTO public.manager(id) VALUES (1238);


INSERT INTO public.notification(id, content, manager_id, creation_date) VALUES (15, 'Срећни божићни и новогодишњи празници!', 1237, '07-01-2020');
INSERT INTO public.notification(id, content, manager_id, creation_date) VALUES (16, 'Колективни годишњи одмор od 01.08. до 05.08!', 1237, '07-15-2022');
