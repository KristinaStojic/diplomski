INSERT INTO ROLES (name) VALUES ('ROLE_ADMIN');
INSERT INTO ROLES (name) VALUES ('ROLE_COUNTER_WORKER');
INSERT INTO ROLES (name) VALUES ('ROLE_MANAGER');
INSERT INTO ROLES (name) VALUES ('ROLE_POSTMAN');
INSERT INTO ROLES (name) VALUES ('ROLE_ACCOUNTING_WORKER');
INSERT INTO ROLES (name) VALUES ('ROLE_CLIENT');

INSERT INTO USERS(id, deleted, email, enabled, last_password_reset_date, name, password, phone_number, surname, role_id, verification_code)
	VALUES (1236, false, 'stojic.kris@gmail.com', true, null,  'Kristina', 'kris', '0643515864', 'Stojic', 1, null);
INSERT INTO USERS(id, deleted, email, enabled, last_password_reset_date, name, password, phone_number, surname, role_id, verification_code)
	VALUES (1237, false, 'huawei5242@gmail.com', true, null,  'Nikola', 'nikola', '06536526541', 'Stojic', 3, null);
INSERT INTO USERS(id, deleted, email, enabled, last_password_reset_date, name, password, phone_number, surname, role_id, verification_code)
	VALUES (1238, false, 'markomarko@gmail.com', true, null,  'Marko', 'marko', '0636523562', 'Markovic', 3, null);
INSERT INTO USERS(id, deleted, email, enabled, last_password_reset_date, name, password, phone_number, surname, role_id, verification_code)
	VALUES (1239, false, 'klaraacim1@gmail.com', true, null,  'Klara', 'klara', '061352652', 'Acimovic', 2, null);
INSERT INTO USERS(id, deleted, email, enabled, last_password_reset_date, name, password, phone_number, surname, role_id, verification_code)
	VALUES (1240, false, 'klijent@gmail.com', true, null,  'Jovan', null, '061352652', 'Jovanovic', 6, null);
INSERT INTO USERS(id, deleted, email, enabled, last_password_reset_date, name, password, phone_number, surname, role_id, verification_code)
	VALUES (1242, false, 'klijent2@gmail.com', true, null,  'Petar', null, '0641352652', 'Jovanovic', 6, null);
INSERT INTO USERS(id, deleted, email, enabled, last_password_reset_date, name, password, phone_number, surname, role_id, verification_code)
 	VALUES (1243, false, 'obracunski@gmail.com', true, null,  'Radmila', 'kris', '0641352652', 'Stojic', 5, null);
INSERT INTO USERS(id, deleted, email, enabled, last_password_reset_date, name, password, phone_number, surname, role_id, verification_code)
	VALUES (1244, false, 'klijent3@gmail.com', true, null,  'Marija', null, '0641352652', 'Markovic', 6, null);
INSERT INTO USERS(id, deleted, email, enabled, last_password_reset_date, name, password, phone_number, surname, role_id, verification_code)
	VALUES (1245, false, 'klijent4@gmail.com', true, null,  'Dusan', null, '0641352652', 'Simic', 6, null);
INSERT INTO USERS(id, deleted, email, enabled, last_password_reset_date, name, password, phone_number, surname, role_id, verification_code)
	VALUES (1246, false, 'salterposta2@gmail.com', true, null,  'Natasa', 'kris', '061352652', 'Mitrovic', 2, null);


INSERT INTO public.country(id, country_name) VALUES (100, 'Srbija');
INSERT INTO public.country(id, country_name) VALUES (101, 'BiH');

INSERT INTO public.city(id, city_name, postal_code, country_id) VALUES (150, 'Novi Sad', 21000, 100);
INSERT INTO public.city(id, city_name, postal_code, country_id) VALUES (151, 'Subotica', 24000, 100);
INSERT INTO public.city(id, city_name, postal_code, country_id) VALUES (152, 'Bijeljina', 35000, 101);
INSERT INTO public.city(id, city_name, postal_code, country_id) VALUES (153, 'Zvornik', 75400, 101);


INSERT INTO public.address(id, latitude, longitude, street, street_number, city_id) VALUES (155, 15.265, 45.235, 'Svetosavska', 10, 150);
INSERT INTO public.address(id, latitude, longitude, street, street_number, city_id) VALUES (156, 17.615, 47.354, 'Balzakova', 11, 151);
INSERT INTO public.address(id, latitude, longitude, street, street_number, city_id) VALUES (157, 47.615, 33.354, 'Svetog Save', 24, 152);
INSERT INTO public.address(id, latitude, longitude, street, street_number, city_id) VALUES (158, 37.615, 52.354, 'Brace Jugovica', 17, 153);


INSERT INTO public.post_office(id, employee_number, phone_number, address_id, deleted) VALUES (33, 8, 062352654, 155, false);
INSERT INTO public.post_office(id, employee_number, phone_number, address_id, deleted) VALUES (34, 11, 0653562545, 156, false);


INSERT INTO public.worker(id, post_office_id) VALUES (1237, 33);
INSERT INTO public.worker(id, post_office_id) VALUES (1239, 33);
INSERT INTO public.worker(id, post_office_id) VALUES (1238, 34);
INSERT INTO public.worker(id, post_office_id) VALUES (1246, 34);
INSERT INTO public.worker(id, post_office_id) VALUES (1243, 33);

INSERT INTO public.manager(id) VALUES (1237);
INSERT INTO public.manager(id) VALUES (1238);

INSERT INTO public.counter_worker(id) VALUES (1239);
INSERT INTO public.counter_worker(id) VALUES (1246);

INSERT INTO public.accounting_worker(id) VALUES (1243);

INSERT INTO public.notification(id, content, manager_id, creation_date) VALUES (15, 'Срећни божићни и новогодишњи празници!', 1237, '07-01-2020');
INSERT INTO public.notification(id, content, manager_id, creation_date) VALUES (16, 'Колективни годишњи одмор od 01.08. до 05.08!', 1237, '07-15-2022');


INSERT INTO public.client(id, address_id) VALUES (1240, 155);
INSERT INTO public.client(id, address_id) VALUES (1242, 155);
INSERT INTO public.client(id, address_id) VALUES (1244, 157);
INSERT INTO public.client(id, address_id) VALUES (1245, 158);



INSERT INTO public.shipment(
	id, email_report, sent_date, letter_type, personal_delivery, return_receipt, shipment_status, shipment_type, email, total_price, value, weight, receiving_post_office_id, delivering_post_office_id, receiver_id, region_id, sender_id, code)
	VALUES (11, false, '02-05-2022', null, true, true, 0, 1, null, 250, 0, 3500, 33, null, 1242, null, 1240, 'AKR3O53M8P');
INSERT INTO public.shipment(
	id, email_report, sent_date, letter_type, personal_delivery, return_receipt, shipment_status, shipment_type, email, total_price, value, weight, receiving_post_office_id, delivering_post_office_id, receiver_id, region_id, sender_id, code)
	VALUES (12, false, '01-08-2022', null, true, true, 1, 1, null, 250, 0, 3500, 33, 34, 1242, null, 1240, 'AMFE305AMF');

INSERT INTO public.shipment(
	id, email_report, sent_date, letter_type, personal_delivery, return_receipt, shipment_status, shipment_type, email, total_price, value, weight, receiving_post_office_id, delivering_post_office_id, receiver_id, region_id, sender_id, code)
	VALUES (15, true, '01-03-2022', 1, true, true, 2, 0, 'huawei5242@gmail.com', 350, 0, 120, 33, null, 1244, null, 1245, 'NFHG24K45N');

INSERT INTO public.shipment(
	id, email_report, sent_date, letter_type, personal_delivery, return_receipt, shipment_status, shipment_type, email, total_price, value, weight, receiving_post_office_id, delivering_post_office_id, receiver_id, region_id, sender_id, code)
	VALUES (16, false, '01-07-2022', 0, true, true, 3, 1, null, 270, 0, 340, 33, null, 1244, null, 1242, 'MGI42D5R7A');

INSERT INTO public.financial_service(id, amount, currency, date, client_id, counter_worker_id) VALUES (11, 1500, 'DIN', '07-01-2022', 1240, 1239);

INSERT INTO public.payment(
	model, payment_code, purpose, receiver_account, receiving_place, reference_number, id, receiver_id)
	VALUES ('2ER', '2451563254', 'Ispiti', '546598563264', 'Zvornik', '542', 11, 1244);

INSERT INTO public.financial_service(id, amount, currency, date, client_id, counter_worker_id) VALUES (12, 5000, 'DIN', '10-11-2021', 1244, 1239);

INSERT INTO public.payment(
	model, payment_code, purpose, receiver_account, receiving_place, reference_number, id, receiver_id)
	VALUES ('3R4', '6548645447', 'Internet', '6598656244', 'Subotica', '234', 12, 1240);


INSERT INTO public.financial_service(id, amount, currency, date, client_id, counter_worker_id) VALUES (13, 3500, 'DIN', '09-05-2022', 1242, 1246);

INSERT INTO public.payment(
	model, payment_code, purpose, receiver_account, receiving_place, reference_number, id, receiver_id)
	VALUES ('RT4', '1564864512', 'Struja', '64687654135', 'Novi Sad', '784', 13, 1240);