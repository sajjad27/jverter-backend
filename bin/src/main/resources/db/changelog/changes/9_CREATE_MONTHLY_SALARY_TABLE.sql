--liquibase formatted sql

--changeset sajad:9 failOnError:false runInTransaction:true

--comment:  the table which has the monthly calculated salary 

create table monthly_salary (
    id bigint not null auto_increment,
	workerid bigint not null,
	month datetime(6) not null,
	regular_working_hours  double precision not null,
	hourly_salary  double precision not null,
	absent_with_excuse_deduction_ratio  double precision not null,
	absent_without_excuse_deduction_ratio  double precision not null,
	lowertime_hours_deduction_ratio  double precision not null,
	overtime_increment_ratio  double precision not null,
	primary key (id)
);
	
alter table monthly_salary 
   add constraint UK_monthly_salary_main unique (workerid, month);
   
alter table monthly_salary 
   add constraint FK_MONTHLY_SALARY_USER
   foreign key (workerid) 
   references user (id);