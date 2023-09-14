--liquibase formatted sql

--changeset sajad:4 failOnError:false runInTransaction:true

--comment:  this is the time interval mentioned in the CREATE_WORK_DAT_TABLE script  

create table work_time (
    id bigint not null auto_increment,
	end_time time not null,
	start_time time not null,
	project_id bigint not null,
	work_day_id bigint not null,
	primary key (id)
);

alter table work_time 
       add constraint FK_WORKTIME_PROJECT
       foreign key (project_id) 
       references project (id);
    
alter table work_time 
   add constraint FK_WORKTIME_WORKDAY 
   foreign key (work_day_id) 
   references work_day (id);