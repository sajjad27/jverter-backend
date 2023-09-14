--liquibase formatted sql

--changeset sajad:19 failOnError:false runInTransaction:true

--comment:  add other deduction column similar to credit

alter table daily_salary 
  add other_deduction double precision not null;
