alter table `order` add uuid varchar(36) not null after id;
update `order` set uuid = UUID();
alter table `order` add constraint uk_order_uuid unique (uuid);
