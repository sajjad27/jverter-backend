--liquibase formatted sql

--changeset sajad:6 failOnError:false runInTransaction:true

--comment:  add timestamp here and is-deleted

alter table work_time 
  add is_deleted integer not null DEFAULT 0;
  
alter table work_time 
  add timestamp datetime(6);