create table `order` (
    id bigint not null auto_increment,
    subtotal decimal(10,2) not null,
    shipping_fee decimal(10,2) not null,
    total_amount decimal(10,2) not null,

    restaurant_id bigint not null,
    customer_user_id bigint not null,
    payment_method_id bigint not null,

    address_city_id bigint(20) not null,
    address_zipcode varchar(9) not null,
    address_street varchar(100) not null,
    address_number varchar(20) not null,
    address_complement varchar(60) null,
    address_neighborhood varchar(60) not null,

    status varchar(10) not null,
    creation_date datetime not null,
    confirmation_date datetime null,
    cancellation_date datetime null,
    delivery_date datetime null,

    primary key (id),

    constraint fk_order_address_city foreign key (address_city_id) references city (id),
    constraint fk_order_restaurant foreign key (restaurant_id) references restaurant (id),
    constraint fk_order_customer_user foreign key (customer_user_id) references user (id),
    constraint fk_order_payment_method foreign key (payment_method_id) references payment_method (id)
) engine=InnoDB default charset=utf8;

create table order_item (
    id bigint not null auto_increment,
    quantity smallint(6) not null,
    unit_price decimal(10,2) not null,
    total_price decimal(10,2) not null,
    note varchar(255) null,
    order_id bigint not null,
    product_id bigint not null,

    primary key (id),
    unique key uk_order_item_product (order_id, product_id),

    constraint fk_order_item_order foreign key (order_id) references `order` (id),
    constraint fk_order_item_product foreign key (product_id) references product (id)
) engine=InnoDB default charset=utf8;
