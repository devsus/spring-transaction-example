package com.transaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transaction.entity.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long>{

}
