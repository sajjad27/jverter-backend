--liquibase formatted sql

--changeset sajad:17 failOnError:false runInTransaction:true

--comment:  Create Bonus table

create table bonus (
    id bigint not null auto_increment,
	amount double precision not null,
	note varchar(255),
	workerid bigint not null,
	timestamp datetime(6) not null,
	is_deleted integer not null DEFAULT 0,
	primary key (id)
);

alter table bonus 
   add constraint FK_BONUS_USER
   foreign key (workerid) 
   references user (id);

