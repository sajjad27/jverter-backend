--liquibase formatted sql

--changeset sajad:1 failOnError:false runInTransaction:true

--comment:  create initial dummy data
insert 
    into
        user
        (username, password, role, email, is_activated) 
    values
        ('sajad', '$2a$10$nxoL6.9n4qNRjZjv7e3CG.YkwWPh.WghqxHIOqWlZL1wASsl9o5aq', 'ROLE_PROGRAMMER', 'sajad-2020@hotmail.com', 1);
		
insert 
    into
        program
        (user_id, title, description, js_code)
    values
        (1, 'Adder', 'This program allows you to add 2 numbers', 'return firstNum + secondNum;');
		
insert 
    into
        input_field
        (program_id, name, label, placeholder, input_field_type)
    values
        (1, 'firstNum', 'First Number', 'First Number', 'NUMBER');
insert 
    into
        input_field
        (program_id, name, label, placeholder, input_field_type)
    values
        (1, 'secondNum', 'Second Number', 'Second Number', 'NUMBER');
		
		
		
insert 
    into
        input_field_validator
        (program_id, input_field_name, Input_validator_type, message)
    values
        (1, 'firstNum', 'REQUIRED', 'First Number can not be null or empty');
		
insert 
    into
        input_field_validator
        (program_id, input_field_name, Input_validator_type, message)
    values
        (1, 'secondNum', 'REQUIRED', 'Second Number can not be null or empty');