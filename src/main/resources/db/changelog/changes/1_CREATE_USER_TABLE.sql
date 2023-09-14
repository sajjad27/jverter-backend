--liquibase formatted sql

--changeset sajad:2 failOnError:true runInTransaction:true

--comment:  create a user table along with a default admin user, will be executed once at the initialization of the app

create table user (
    id bigint not null auto_increment,
	username varchar(25) not null unique,
	password varchar(255) not null,
	role varchar(20) not null,
	email varchar(55) not null,
    is_activated TINYINT(1) NOT NULL CHECK (is_activated IN (0, 1)),
	primary key (id)
);