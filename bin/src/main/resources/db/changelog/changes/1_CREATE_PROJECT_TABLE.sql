--liquibase formatted sql

--changeset sajad:1 failOnError:false runInTransaction:true

--comment:  create project table with contain the work station information

create table project (
    id bigint not null auto_increment,
	code varchar(255) not null,
	description varchar(255),
	location varchar(255) not null,
	primary key (id)
);
	
alter table project 
       add constraint UK_PROJECT_CODE unique (code);
