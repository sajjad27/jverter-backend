--liquibase formatted sql

--changeset sajad:3 failOnError:false runInTransaction:true

--comment:  create the work_day which containt the working day for a specific worker (or user) and where did he spend the day along with thetime interval

create table work_day (
    id bigint not null auto_increment,
	absent integer,
	day date not null,
	note varchar(255),
	workerid bigint not null,
	primary key (id)
);
	
alter table work_day 
       add constraint UK_WORKDAY_WORKERID_DAY unique (workerid, day);
    
alter table work_day 
   add constraint FK_WORKDAY_USER
   foreign key (workerid) 
   references user (id);