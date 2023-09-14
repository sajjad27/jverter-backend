--liquibase formatted sql

--changeset sajad:1 failOnError:false runInTransaction:true

--comment:  create input_field_validator table contains list of validators for input_field table

create table input_field_validator (
	program_id bigint not null,
	input_field_name varchar(55) not null,
	input_validator_type varchar(25),
	message varchar(155) not null,
	PRIMARY KEY (program_id, input_field_name, input_validator_type),
	FOREIGN KEY (program_id) REFERENCES program(id)
);
