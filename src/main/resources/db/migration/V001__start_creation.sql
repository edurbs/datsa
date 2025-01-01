create table city (
    id bigint not null auto_increment,
    name varchar(255),
    state_id bigint,
    primary key (id)
) engine = InnoDB;

create table group_system (
    id bigint not null auto_increment,
    description varchar(255) not null,
    name varchar(255) not null,
    primary key (id)
) engine = InnoDB;

create table group_permission (
    group_id bigint not null,
    permission_id bigint not null
) engine = InnoDB;

create table kitchen (
    id bigint not null auto_increment,
    name varchar(255),
    primary key (id)
) engine = InnoDB;

create table payment_method (
    id bigint not null auto_increment,
    description varchar(255),
    primary key (id)
) engine = InnoDB;

create table permission (
    id bigint not null auto_increment,
    description varchar(255),
    name varchar(255),
    primary key (id)
) engine = InnoDB;

create table product (
    id bigint not null auto_increment,
    active bit not null,
    description varchar(255) not null,
    name varchar(255) not null,
    price decimal(19, 2) not null,
    restaurant_id bigint not null,
    primary key (id)
) engine = InnoDB;

create table restaurant (
    id bigint not null auto_increment,
    address_complement varchar(255),
    address_neighborhood varchar(255),
    address_number varchar(255),
    address_street varchar(255),
    address_zip_code varchar(255),
    last_update_date datetime not null,
    name varchar(255) not null,
    registration_date datetime not null,
    shipping_fee decimal(19, 2) not null,
    address_city_id bigint,
    kitchen_id bigint not null,
    primary key (id)
) engine = InnoDB;

create table restaurant_payment_method (
    restaurant_id bigint not null,
    payment_method_id bigint not null,
    primary key (restaurant_id, payment_method_id)
) engine = InnoDB;

create table state (
    id bigint not null auto_increment,
    name varchar(255),
    primary key (id)
) engine = InnoDB;

create table user (
    id bigint not null auto_increment,
    email varchar(255) not null,
    last_update_date datetime not null,
    name varchar(255) not null,
    password varchar(255) not null,
    registration_date datetime not null,
    primary key (id)
) engine = InnoDB;

create table user_group (
    user_id bigint not null,
    group_id bigint not null
) engine = InnoDB;

alter table city
add constraint FK_city_state foreign key (state_id) references state (id);

alter table group_permission
add constraint FK_group_permission_permission foreign key (permission_id) references permission (id);

alter table group_permission
add constraint FK_group_permission_group foreign key (group_id) references group_system (id);

alter table product
add constraint FK_product_restaurant foreign key (restaurant_id) references restaurant (id);

alter table restaurant
add constraint FK_restaurant_address_city foreign key (address_city_id) references city (id);

alter table restaurant
add constraint FK_restaurant_kitchen foreign key (kitchen_id) references kitchen (id);

alter table restaurant_payment_method
add constraint FK_restaurant_payment_method foreign key (payment_method_id) references payment_method (id);

alter table restaurant_payment_method
add constraint FK_restaurant_payment_method_restaurant foreign key (restaurant_id) references restaurant (id);

alter table user_group
add constraint FK_user_group_group foreign key (group_id) references group_system (id);

alter table user_group
add constraint FK_user_group_user foreign key (user_id) references user (id);
