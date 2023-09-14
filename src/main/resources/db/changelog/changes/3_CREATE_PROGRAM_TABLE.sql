--liquibase formatted sql

--changeset sajad:1 failOnError:false runInTransaction:true

--comment:  create program table with contain the program metadata

create table program (
    id bigint not null auto_increment primary key,
	user_id  bigint,
	title varchar(55) not null,
	description varchar(255),
	js_code varchar(1255) not null,
	FOREIGN KEY (user_id) REFERENCES user(id)
);
