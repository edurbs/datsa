ALTER TABLE datsa.payment_method ADD updated_at DATETIME NULL;
update datsa.payment_method pm set updated_at = utc_timestamp;
alter table datsa.payment_method modify updated_at DATETIME NOT NULL;
