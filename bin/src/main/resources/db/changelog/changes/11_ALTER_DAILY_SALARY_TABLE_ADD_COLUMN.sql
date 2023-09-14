--liquibase formatted sql

--changeset sajad:11 failOnError:false runInTransaction:true

--comment:  add two columns, vacation as boolean integer and projects as a string 

alter table daily_salary 
  add vacation integer not null,
  add projects varchar(500) not null;
