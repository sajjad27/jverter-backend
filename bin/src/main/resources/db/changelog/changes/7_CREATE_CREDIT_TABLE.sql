--liquibase formatted sql

--changeset sajad:7 failOnError:false runInTransaction:true

--comment:  credit table where all loans are taken by a worker

create table credit (
    id bigint not null auto_increment,
	amount double precision not null,
	note varchar(255) not null,
	workerid bigint not null,
	timestamp datetime(6) not null,
	is_deleted integer not null DEFAULT 0,
	primary key (id)
);

alter table credit 
   add constraint FK_CREDIT_USER
   foreign key (workerid) 
   references user (id);