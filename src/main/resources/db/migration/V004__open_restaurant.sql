alter table restaurant add column open boolean not null;
update restaurant set open=false;