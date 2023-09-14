--liquibase formatted sql

--changeset sajad:15 failOnError:false runInTransaction:true

--comment:  Add column IBAN to user table

alter table user 
  add iban varchar(500);

