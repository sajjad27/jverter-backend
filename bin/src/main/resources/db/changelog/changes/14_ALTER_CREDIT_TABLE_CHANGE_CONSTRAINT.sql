--liquibase formatted sql

--changeset sajad:14 failOnError:false runInTransaction:true

--comment:  Change constraint, note is optional in credit table

ALTER TABLE credit 
MODIFY note VARCHAR(255) NULL;

