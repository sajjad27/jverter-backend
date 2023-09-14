--liquibase formatted sql

--changeset sajad:18 failOnError:false runInTransaction:true

--comment:  add bonus column similar to credit

alter table daily_salary 
  add bonus double precision not null;
