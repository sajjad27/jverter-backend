--liquibase formatted sql

--changeset sajad:2 failOnError:true runInTransaction:true

--comment:  create audit log to be used by everybody

CREATE TABLE audit_log (
    audit_log_id INT PRIMARY KEY AUTO_INCREMENT,
    table_name VARCHAR(255) NOT NULL,
    remarks VARCHAR(255),
    action VARCHAR(10) NOT NULL, -- Possible values: 'INSERT', 'UPDATE', 'DELETE'
    old_data JSON, -- Store the old data before the change as JSON
    new_data JSON, -- Store the new data after the change as JSON
    changed_by VARCHAR(255) NOT NULL,
    changed_at DATETIME NOT NULL,
    INDEX ix_audit_log_table_name (table_name),
    INDEX ix_audit_log_changed_at (changed_at)
);

