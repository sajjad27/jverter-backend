package com.jverter.shared.auditlog.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuditLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "audit_log_id")
	private int auditLogID;

	@Column(name = "table_name", nullable = false)
	private String tableName;

	@Column(name = "remarks")
	private String recordID;

	@Column(name = "action", nullable = false)
	private String action;

	@Column(name = "old_data", columnDefinition = "JSON")
	private String oldData;

	@Column(name = "new_data", columnDefinition = "JSON")
	private String newData;

	@Column(name = "changed_by", nullable = false)
	private String changedBy;

	@Column(name = "changed_at", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date changedAt;


}
