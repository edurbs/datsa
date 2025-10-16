set foreign_key_checks = 0;
lock tables city write, kitchen write, `state` write, payment_method write,
	group_system write, group_permission write, permission write,
	product write, restaurant write, restaurant_payment_method write,
	restaurant_user write, `user` write, user_group write,
	`order` write, order_item write, product_photo write, oauth2_registered_client write;
delete from city;
delete from kitchen;
delete from `state`;
delete from payment_method;
delete from group_system;
delete from group_permission;
delete from permission;
delete from product;
delete from restaurant;
delete from restaurant_payment_method;
delete from restaurant_user;
delete from `user`;
delete from user_group;
delete from `order`;
delete from order_item;
delete from product_photo;
delete from oauth2_registered_client;

set foreign_key_checks = 1;
alter table city auto_increment = 1;
alter table kitchen auto_increment = 1;
alter table state auto_increment = 1;
alter table payment_method auto_increment = 1;
alter table group_system auto_increment = 1;
alter table permission auto_increment = 1;
alter table product auto_increment = 1;
alter table restaurant auto_increment = 1;
alter table user auto_increment = 1;
alter table `order` auto_increment = 1;
alter table order_item auto_increment = 1;
insert ignore into kitchen (id, name)
values (1, 'Tailandesa');
insert ignore into kitchen (id, name)
values (2, 'Indiana');
insert ignore into state (id, name)
values (1, 'Minas Gerais');
insert ignore into state (id, name)
values (2, 'São Paulo');
insert ignore into state (id, name)
values (3, 'Ceará');
insert ignore into city (id, name, state_id)
values (1, 'Uberlândia', 1);
insert ignore into city (id, name, state_id)
values (2, 'Belo Horizonte', 1);
insert ignore into city (id, name, state_id)
values (3, 'São Paulo', 2);
insert ignore into city (id, name, state_id)
values (4, 'Campinas', 2);
insert ignore into city (id, name, state_id)
values (5, 'Fortaleza', 3);
insert ignore into payment_method (id, description, updated_at)
values (1, 'Cartão de crédito', utc_timestamp);
insert ignore into payment_method (id, description, updated_at)
values (2, 'Cartão de débito', utc_timestamp);
insert ignore into payment_method (id, description, updated_at)
values (3, 'Dinheiro', utc_timestamp);

insert into permission (id, name, description) values (2, 'EDIT_KITCHENS', 'Allows editing kitchens');
insert into permission (id, name, description) values (4, 'EDIT_PAYMENT_METHODS', 'Allows creating or editing payment methods');
insert into permission (id, name, description) values (6, 'EDIT_CITIES', 'Allows creating or editing cities');
insert into permission (id, name, description) values (8, 'EDIT_STATES', 'Allows creating or editing states');
insert into permission (id, name, description) values (9, 'CONSULT_USERS_GROUPS_PERMISSIONS', 'Allows consulting users');
insert into permission (id, name, description) values (10, 'EDIT_USERS_GROUPS_PERMISSIONS', 'Allows creating or editing users');
insert into permission (id, name, description) values (12, 'EDIT_RESTAURANTS', 'Allows creating, editing or managing restaurants');
insert into permission (id, name, description) values (15, 'CONSULT_ORDERS', 'Allows consulting orders');
insert into permission (id, name, description) values (16, 'MANAGE_ORDERS', 'Allows managing orders');
insert into permission (id, name, description) values (17, 'GENERATE_REPORTS', 'Allows generating reports');

insert ignore into restaurant (
        id,
        name,
        shipping_fee,
        kitchen_id,
        registration_date,
        last_update_date,
        `active`,
        address_city_id,
        address_zip_code,
        address_street,
        address_number,
        address_neighborhood
    )
values (
        1,
        'Thai Gourmet',
        10,
        1,
        utc_timestamp,
        utc_timestamp,
        1,
        1,
        '38400-999',
        'Rua João Pinheiro',
        '1000',
        'Centro'
    );
insert ignore into restaurant (
        id,
        name,
        shipping_fee,
        kitchen_id,
        registration_date,
        last_update_date,
        `active`
    )
values (
        2,
        'Thai Delivery',
        9.50,
        1,
        utc_timestamp,
        utc_timestamp,
        1
    );
insert ignore into restaurant (
        id,
        name,
        shipping_fee,
        kitchen_id,
        registration_date,
        last_update_date,
        `active`
    )
values (
        3,
        'Tuk Tuk Comida Indiana',
        15,
        2,
        utc_timestamp,
        utc_timestamp,
        1
    );
insert ignore into kitchen (id, name)
values (3, 'Argentina');
insert ignore into kitchen (id, name)
values (4, 'Brasileira');
insert ignore into restaurant (
        id,
        name,
        shipping_fee,
        kitchen_id,
        registration_date,
        last_update_date,
        `active`
    )
values (
        4,
        'Java Steakhouse',
        12,
        3,
        utc_timestamp,
        utc_timestamp,
        0
    );
insert ignore into restaurant (
        id,
        name,
        shipping_fee,
        kitchen_id,
        registration_date,
        last_update_date,
        `active`
    )
values (
        5,
        'Lanchonete do Tio Sam',
        11,
        4,
        utc_timestamp,
        utc_timestamp,
        1
    );
insert ignore into restaurant (
        id,
        name,
        shipping_fee,
        kitchen_id,
        registration_date,
        last_update_date,
        `active`
    )
values (
        6,
        'Bar da Maria',
        6,
        4,
        utc_timestamp,
        utc_timestamp,
        1
    );
insert ignore into product (
        name,
        DESCRIPTION,
        price,
        ACTIVE,
        restaurant_id
    )
values (
        'Porco com molho agridoce',
        'Deliciosa carne suína ao molho especial',
        78.90,
        1,
        1
    );
insert ignore into product (
        name,
        DESCRIPTION,
        price,
        ACTIVE,
        restaurant_id
    )
values (
        'Camarão tailandês',
        '16 camarões grandes ao molho picante',
        110,
        0,
        1
    );
insert ignore into product (
        name,
        DESCRIPTION,
        price,
        ACTIVE,
        restaurant_id
    )
values (
        'Salada picante com carne grelhada',
        'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha',
        87.20,
        1,
        2
    );
insert ignore into product (
        name,
        DESCRIPTION,
        price,
        ACTIVE,
        restaurant_id
    )
values (
        'Garlic Naan',
        'Pão tradicional indiano com cobertura de alho',
        21,
        1,
        3
    );
insert ignore into product (
        name,
        DESCRIPTION,
        price,
        ACTIVE,
        restaurant_id
    )
values (
        'Murg Curry',
        'Cubos de frango preparados com molho curry e especiarias',
        43,
        1,
        3
    );
insert ignore into product (
        name,
        DESCRIPTION,
        price,
        ACTIVE,
        restaurant_id
    )
values (
        'Bife Ancho',
        'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé',
        79,
        1,
        4
    );
insert ignore into product (
        name,
        DESCRIPTION,
        price,
        ACTIVE,
        restaurant_id
    )
values (
        'T-Bone',
        'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon',
        89,
        1,
        4
    );
insert ignore into product (
        name,
        DESCRIPTION,
        price,
        ACTIVE,
        restaurant_id
    )
values (
        'Sanduíche X-Tudo',
        'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese',
        19,
        1,
        5
    );
insert ignore into product (
        name,
        DESCRIPTION,
        price,
        ACTIVE,
        restaurant_id
    )
values (
        'Espetinho de Cupim',
        'Acompanha farinha, mandioca e vinagrete',
        8,
        1,
        6
    );
insert ignore into restaurant_payment_method (
        restaurant_id,
        payment_method_id
    )
values (1, 1),
    (1, 2),
    (1, 3),
    (2, 3),
    (3, 2),
    (3, 3),
    (4, 1),
    (4, 2),
    (5, 1),
    (5, 2),
    (6, 3);
insert into group_system (id, name, description) values (1, 'Gerente', "Grupo do gerente"), (2, 'Vendedor', "grupos dos vendedores"), (3, 'Secretária', "grupo das secretárias"), (4, 'Cadastrador', "grupos dos cadastradores");

# Adiciona todas as permissoes no grupo do gerente
insert into group_permission (group_id, permission_id)
select 1, id from permission;

# Adiciona permissoes no grupo do vendedor
insert into group_permission (group_id, permission_id)
select 2, id from permission where name like 'CONSULT_%';

# Adiciona permissoes no grupo do auxiliar
insert into group_permission (group_id, permission_id)
select 3, id from permission where name like 'CONSULT_%';

# Adiciona permissoes no grupo cadastrador
insert into group_permission (group_id, permission_id)
select 4, id from permission where name like '%_RESTAURANTS' or name like '%_PRODUCTS';


INSERT IGNORE INTO user (id, name, email, password, registration_date,
        last_update_date)
VALUES (1, 'Eduardo', 'eduardo.ger@gmail.com', '$2a$12$26pOnOiAOVDqK58WelYc2exCcyQiOTEMZF8JeQjNASB.E5/g1W.j.', utc_timestamp,
        utc_timestamp);
INSERT IGNORE INTO user (id, name, email, password, registration_date,
        last_update_date)
VALUES (2, 'Maria', 'maria.vnd@gmail.com', '$2a$12$26pOnOiAOVDqK58WelYc2exCcyQiOTEMZF8JeQjNASB.E5/g1W.j.', utc_timestamp,
        utc_timestamp);
INSERT IGNORE INTO user (id, name, email, password, registration_date,
        last_update_date)
VALUES (3, 'José', 'jose.aux@gmail.com', '$2a$12$26pOnOiAOVDqK58WelYc2exCcyQiOTEMZF8JeQjNASB.E5/g1W.j.', utc_timestamp,
        utc_timestamp);
INSERT IGNORE INTO user (id, name, email, password, registration_date,
        last_update_date)
VALUES (4, 'João', 'joao.cad@gmail.com', '$2a$12$26pOnOiAOVDqK58WelYc2exCcyQiOTEMZF8JeQjNASB.E5/g1W.j.', utc_timestamp,
        utc_timestamp);
INSERT IGNORE INTO user (id, name, email, password, registration_date,
        last_update_date)
VALUES (5, 'Manoel', 'manoel.loja@gmail.com', '$2a$12$26pOnOiAOVDqK58WelYc2exCcyQiOTEMZF8JeQjNASB.E5/g1W.j.', utc_timestamp,
        utc_timestamp);
INSERT IGNORE INTO user (id, name, email, password, registration_date,
        last_update_date)
VALUES (6, 'Joaquim', 'joaquim@gmail.com', '$2a$12$26pOnOiAOVDqK58WelYc2exCcyQiOTEMZF8JeQjNASB.E5/g1W.j.', utc_timestamp,
        utc_timestamp);
INSERT IGNORE INTO user (id, name, email, password, registration_date,
        last_update_date)
VALUES (7, 'Benedito', 'benedito@gmail.com', '$2a$12$26pOnOiAOVDqK58WelYc2exCcyQiOTEMZF8JeQjNASB.E5/g1W.j.', utc_timestamp,
        utc_timestamp);

INSERT ignore into user_group (user_id, group_id) values (1,1);
INSERT ignore into user_group (user_id, group_id) values (1,2);
INSERT ignore into user_group (user_id, group_id) values (2,2);
INSERT ignore into user_group (user_id, group_id) values (3,3);
INSERT ignore into user_group (user_id, group_id) values (4,4);
INSERT ignore into restaurant_user (restaurant_id, user_id) values (1,1);
INSERT ignore into restaurant_user (restaurant_id, user_id) values (2,1);
INSERT ignore into restaurant_user (restaurant_id, user_id) values (2,2);
INSERT ignore into restaurant_user (restaurant_id, user_id) values (3,1);
INSERT ignore into restaurant_user (restaurant_id, user_id) values (3,2);
INSERT ignore into restaurant_user (restaurant_id, user_id) values (3,3);
insert into `order` (id, uuid, restaurant_id, customer_user_id, payment_method_id, address_city_id, address_zip_code,
    address_street, address_number, address_complement, address_neighborhood,
    status, creation_date, subtotal, shipping_fee, total_amount)
values (1, '6609f1f9-443f-4735-bedd-b24bd06811fb', 1, 1, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801', 'Brasil',
'CREATED', "2025-09-05 00:24:09", 298.90, 10, 308.90);

insert into order_item (id, order_id, product_id, quantity, unit_price, total_price, note)
values (1, 1, 1, 1, 78.9, 78.9, null);

insert into order_item (id, order_id, product_id, quantity, unit_price, total_price, note)
values (2, 1, 2, 2, 110, 220, 'Menos picante, por favor');


insert into `order` (id, uuid, restaurant_id, customer_user_id, payment_method_id, address_city_id, address_zip_code,
        address_street, address_number, address_complement, address_neighborhood,
        status, creation_date, subtotal, shipping_fee, total_amount)
values (2, '3e2f2a9a-4c2f-4385-b581-324432ecaa36', 4, 1, 2, 1, '38400-111', 'Rua Acre', '300', 'Casa 2', 'Centro',
'CREATED', "2025-09-05 00:24:09", 79, 0, 79);

insert into order_item (id, order_id, product_id, quantity, unit_price, total_price, note)
values (3, 2, 6, 1, 79, 79, 'Ao ponto');

insert into `order` (id, uuid, restaurant_id, customer_user_id, payment_method_id, address_city_id, address_zip_code,
    address_street, address_number, address_complement, address_neighborhood,
    status, creation_date, subtotal, shipping_fee, total_amount)
values (3, 'a1b2c3d4-e5f6-7890-abcd-1234567890ab', 2, 2, 3, 2, '38400-222', 'Rua das Flores', '150', null, 'Jardim',
'CONFIRMED', "2025-09-06 00:24:09", 87.20, 9.50, 96.70);

insert into order_item (id, order_id, product_id, quantity, unit_price, total_price, note)
values (4, 3, 3, 1, 87.20, 87.20, 'Sem pimenta');

insert into `order` (id, uuid, restaurant_id, customer_user_id, payment_method_id, address_city_id, address_zip_code,
    address_street, address_number, address_complement, address_neighborhood,
    status, creation_date, subtotal, shipping_fee, total_amount)
values (4, 'b2c3d4e5-f6a1-8901-bcda-2345678901bc', 3, 3, 2, 3, '38400-333', 'Av. Brasil', '200', 'Bloco B', 'Centro',
'DELIVERED', "2025-09-07 00:24:09", 64.00, 15.00, 79.00);

insert into order_item (id, order_id, product_id, quantity, unit_price, total_price, note)
values (5, 4, 4, 2, 21.00, 42.00, null);

insert into order_item (id, order_id, product_id, quantity, unit_price, total_price, note)
values (6, 4, 5, 1, 43.00, 43.00, 'Pouco sal');

insert into `order` (id, uuid, restaurant_id, customer_user_id, payment_method_id, address_city_id, address_zip_code,
    address_street, address_number, address_complement, address_neighborhood,
    status, creation_date, subtotal, shipping_fee, total_amount)
values (5, 'c3d4e5f6-a1b2-9012-cdab-3456789012cd', 6, 2, 1, 5, '38400-444', 'Rua do Sol', '50', null, 'Praia',
'CANCELLED', "2025-09-07 00:24:09", 16.00, 6.00, 22.00);

insert into order_item (id, order_id, product_id, quantity, unit_price, total_price, note)
values (7, 5, 9, 2, 8.00, 16.00, 'Bem passado');

INSERT INTO oauth2_registered_client
(id, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_name, client_authentication_methods, authorization_grant_types, redirect_uris, post_logout_redirect_uris, scopes, client_settings, token_settings)
VALUES('1', 'datsa-backend', '2025-10-16 16:51:29', '$2a$10$aSeBs1Orx0nsZJfhqZdRdeSIRCUIK6V4mvT8KrNYJL4Bu/IEW0Uw.', NULL, 'Datsa Backend', 'client_secret_basic', 'client_credentials', '', NULL, 'READ', '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}', '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",1800.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.core.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",3600.000000000]}');

INSERT INTO oauth2_registered_client
(id, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_name, client_authentication_methods, authorization_grant_types, redirect_uris, post_logout_redirect_uris, scopes, client_settings, token_settings)
VALUES('2', 'datsa-web', '2025-10-16 16:51:29', '$2a$10$AjhJiqK3UfNSxQ/XmErlS.q/YGk2Nn5d6iY32V2BX0aOvxWlbvwla', NULL, 'Datsa Web', 'client_secret_basic', 'refresh_token,authorization_code', 'http://127.0.0.1:8080/swagger-ui/oauth2-redirect.html,http://127.0.0.1:8080/authorized', NULL, 'READ,WRITE', '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":true}', '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":false,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",900.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.core.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",86400.000000000]}');

INSERT INTO oauth2_registered_client
(id, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_name, client_authentication_methods, authorization_grant_types, redirect_uris, post_logout_redirect_uris, scopes, client_settings, token_settings)
VALUES('3', 'datsaAnalytics', '2025-10-16 16:51:29', '$2a$10$RbpHEX8bMmvwucuKT8gKNu1ZN3zChon.eTQHWuNdRSroCfRVLY/fy', NULL, 'Datsa Analytics', 'client_secret_basic', 'authorization_code', 'http://localhost:8080', NULL, 'READ,WRITE', '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":true}', '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",1800.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.core.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",3600.000000000]}');

unlock tables;
