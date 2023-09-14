--liquibase formatted sql

--changeset sajad:13 failOnError:false runInTransaction:true

--comment:  assign worker to the assets by another worker

create table asset_worker_assignment (
    id bigint not null auto_increment,
	assignedBy bigint not null,
	assignedTo bigint not null,
	assetId bigint not null,
	assigned integer not null,
	timestamp datetime(6) not null,
	primary key (id)
);
	   
alter table asset_worker_assignment 
   add constraint FK_ASSIGN_BY_USERID
   foreign key (assignedBy) 
   references user (id);
   
alter table asset_worker_assignment 
   add constraint FK_ASSIGN_TO_USERID
   foreign key (assignedTo) 
   references user (id);
   
alter table asset_worker_assignment 
   add constraint FK_ASSIGN_ASSEID
   foreign key (assetId) 
   references user (id);
