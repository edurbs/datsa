alter table restaurant add column open boolean not null default true;
update restaurant set open=false;