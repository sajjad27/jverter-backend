--liquibase formatted sql

--changeset sajad:1 failOnError:false runInTransaction:true

--comment:  create program input fields table with containing the input fileds in program table

create table input_field (
	program_id bigint not null,
	name varchar(55) unique not null,
	label varchar(55),
	placeholder varchar(155) not null,
	input_field_type varchar(25) not null,
	PRIMARY KEY (program_id, name),
	FOREIGN KEY (program_id) REFERENCES program(id),
    INDEX idx_name (name)
);
