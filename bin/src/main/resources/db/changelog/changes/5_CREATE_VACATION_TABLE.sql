--liquibase formatted sql

--changeset sajad:5 failOnError:false runInTransaction:true

--comment:  this is the time interval mentioned in the CREATE_WORK_DAT_TABLE script  

create table vacation (
    id bigint not null auto_increment,
	from_day datetime(6) not null,
	to_day datetime(6) not null,
	workerid bigint not null,
	timestamp datetime(6) not null,
	is_deleted integer not null DEFAULT 0,
	primary key (id)
);

alter table vacation 
   add constraint FK_VACATION_USER
   foreign key (workerid) 
   references user (id);