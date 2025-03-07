set foreign_key_checks = 0;
delete from city;
delete from kitchen;
delete from state;
delete from payment_method;
delete from group_system;
delete from group_permission;
delete from permission;
delete from product;
delete from restaurant;
delete from restaurant_payment_method;
delete from user;
delete from user_group;
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
insert ignore into payment_method (id, description)
values (1, 'Cartão de crédito');
insert ignore into payment_method (id, description)
values (2, 'Cartão de débito');
insert ignore into payment_method (id, description)
values (3, 'Dinheiro');
insert ignore into permission (id, name, description)
values (
        1,
        'CONSULTAR_COZINHAS',
        'Permite consultar cozinhas'
    );
insert ignore into permission (id, name, description)
values (
        2,
        'EDITAR_COZINHAS',
        'Permite editar cozinhas'
    );
insert ignore into permission (id, name, description)
values (
        3,
        'EXCLUIR_COZINHAS',
        'Permite excluir cozinhas'
    );
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
INSERT IGNORE INTO group_system (id, name, description)
values (
        1,
        'ADMIN',
        'Administrador do sistema'
    );
INSERT IGNORE INTO group_system (id, name, description)
values (
        2,
        'USER',
        'Usuário do sistema'
    );
INSERT IGNORE INTO group_system (id, name, description)
values (
        3,
        'USER',
        'Gerente do restaurante'
    );
INSERT IGNORE INTO group_system (id, name, description)
values (
        4,
        'SELLER',
        'Vendedor do restaurante'
    );
INSERT IGNORE INTO user (id, name, email, password, registration_date,
        last_update_date)
VALUES (1, 'Eduardo', 'edu77@asd.com', '12345678', utc_timestamp,
        utc_timestamp);
INSERT IGNORE INTO user (id, name, email, password, registration_date,
        last_update_date)
VALUES (2, 'Maria', 'maria2@asd.com', '12345678', utc_timestamp,
        utc_timestamp);
INSERT IGNORE INTO user (id, name, email, password, registration_date,
        last_update_date)
VALUES (3, 'José', 'jose@asd.com', '12345678', utc_timestamp,
        utc_timestamp);
INSERT ignore into group_permission (group_id, permission_id) VALUES (1,1);
INSERT ignore into group_permission (group_id, permission_id) VALUES (1,2);
INSERT ignore into group_permission (group_id, permission_id) VALUES (1,3);
INSERT ignore into group_permission (group_id, permission_id) VALUES (2,1);
INSERT ignore into group_permission (group_id, permission_id) VALUES (2,2);
INSERT ignore into group_permission (group_id, permission_id) VALUES (3,1);
INSERT ignore into user_group (user_id, group_id) values (1,1);
INSERT ignore into user_group (user_id, group_id) values (1,2);
INSERT ignore into user_group (user_id, group_id) values (1,3);
INSERT ignore into user_group (user_id, group_id) values (2,3);
INSERT ignore into user_group (user_id, group_id) values (2,4);
INSERT ignore into user_group (user_id, group_id) values (3,4);
INSERT ignore into restaurant_user (restaurant_id, user_id) values (1,1);
INSERT ignore into restaurant_user (restaurant_id, user_id) values (2,1);
INSERT ignore into restaurant_user (restaurant_id, user_id) values (2,2);
INSERT ignore into restaurant_user (restaurant_id, user_id) values (3,1);
INSERT ignore into restaurant_user (restaurant_id, user_id) values (3,2);
INSERT ignore into restaurant_user (restaurant_id, user_id) values (3,3);
