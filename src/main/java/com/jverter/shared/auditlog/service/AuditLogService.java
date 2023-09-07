package com.jverter.shared.auditlog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jverter.shared.auditlog.model.entity.AuditLog;
import com.jverter.shared.auditlog.repository.AuditLogRepository;
import com.jverter.shared.helper.DateHelper;

@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    @Autowired
    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public void saveAuditLog(String tableName, String action, String oldData, String newData, String username) {
        AuditLog auditLog = new AuditLog();
        auditLog.setTableName(tableName);
        auditLog.setAction(action);
        auditLog.setOldData(oldData);
        auditLog.setNewData(newData);
        auditLog.setChangedBy(username);
        auditLog.setChangedAt(DateHelper.getCurrentTimestamp());

        auditLogRepository.save(auditLog);
    }
}
