--liquibase formatted sql

--changeset sajad:12 failOnError:false runInTransaction:true

--comment:  create asset table 

create table asset (
    id bigint not null auto_increment,
	code varchar(255) not null,
	description varchar(255),
	name varchar(255) not null,
	primary key (id)
);
	
alter table asset 
       add constraint UK_ASSET_CODE unique (code);