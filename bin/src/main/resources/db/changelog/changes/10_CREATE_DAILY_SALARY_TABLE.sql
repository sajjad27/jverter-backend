--liquibase formatted sql

--changeset sajad:10 failOnError:false runInTransaction:true

--comment:  the table which has the daily details of the monthly salary 

create table daily_salary (
    id bigint not null auto_increment,
	monthly_salary_id bigint not null,
	date datetime(6) not null,
	actual_working_hours double precision not null,
	overtime_hours double precision not null,
	lowertime_hours double precision not null,
	absent_without_excuse integer not null,
	absent_with_excuse integer not null,
	future_date_calculated integer not null,
	weekend integer not null,
	total_daily_worth double precision not null,
	credit double precision not null,
	primary key (id)
);
	
	
alter table daily_salary 
   add constraint UK_daily_salary_main unique (monthly_salary_id, date);
   
alter table daily_salary 
   add constraint FK_DAILYSALARY_MONTHLYSALARY
   foreign key (monthly_salary_id) 
   references monthly_salary (id);
