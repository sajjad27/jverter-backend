--liquibase formatted sql

--changeset sajad:2 failOnError:true runInTransaction:true

--comment:  create a user role table to make each user with its roles

CREATE TABLE user_role (
    user_id BIGINT NOT NULL,
    role VARCHAR(20) NOT NULL,
    PRIMARY KEY (user_id, role),
    FOREIGN KEY (user_id) REFERENCES user(id)
);

insert 
    into
        user_role
        (user_id, role) 
    values
        (1, 'ADMIN');