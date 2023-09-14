--liquibase formatted sql

--changeset sajad:2 failOnError:true runInTransaction:true

--comment:  create a user table along with a default admin user, will be executed once at the initialization of the app

create table user (
    id bigint not null auto_increment,
	username varchar(255) not null,
	password varchar(255) not null,
	role varchar(255) not null,
	full_name varchar(255) not null,
	email varchar(255),
	phone_number varchar(255) not null,
	hiring_date datetime(6),
	iqama_expiration_date datetime(6),
	iqama_number varchar(255),
	monthly_salary double precision not null,
	daily_cost double precision not null,
	nationality varchar(255),
	occupation varchar(255),
	is_activated integer not null,
	primary key (id)
);
	
alter table user 
   add constraint UK_sb8bbouer5wak8vyiiy4pf2bx unique (username);
	
insert 
    into
        user
        (username, password, full_name, phone_number, role, monthly_salary, daily_cost, is_activated ) 
    values
        ('ali', '$2a$10$gB9K8XxuDhandsv4/UFKwu7y5gffbjTsqqukMHhMbhmLiSaEYQzJa', 'ali', '0541445400', 'ROLE_ADMIN', 0, 0, 1);