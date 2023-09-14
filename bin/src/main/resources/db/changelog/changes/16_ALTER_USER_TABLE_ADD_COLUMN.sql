--liquibase formatted sql

--changeset sajad:16 failOnError:false runInTransaction:true

--comment:  Add column EmployeeId to user table

alter table user 
  add employee_Id varchar(44);

