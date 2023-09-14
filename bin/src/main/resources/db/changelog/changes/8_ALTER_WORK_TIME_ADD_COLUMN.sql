--liquibase formatted sql

--changeset sajad:8 failOnError:false runInTransaction:true

--comment:  add work type to work time (mandate)

alter table work_time 
  add work_type varchar(255);
  
  
UPDATE ferp.work_time
  SET work_type = "PLUMPING";
  
  
ALTER TABLE ferp.work_time
  MODIFY work_type varchar(255) NOT NULL;