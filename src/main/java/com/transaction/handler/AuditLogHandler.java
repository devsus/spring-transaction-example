package com.transaction.handler;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.transaction.entity.AuditLog;
import com.transaction.entity.Order;
import com.transaction.repository.AuditLogRepository;

@Component
public class AuditLogHandler {
	
	@Autowired
	private AuditLogRepository auditLogRepository;
	
	//Log audit details (runs in an independent transaction)
	@Transactional(propagation = Propagation.REQUIRES_NEW) 
	public void logAuditDetails(Order order,String action) {
		AuditLog auditLog = new AuditLog();
		auditLog.setAction(action);
		auditLog.setTimestamp(LocalDateTime.now());
		auditLog.setOrderId(Long.valueOf(order.getId()));
		//save the audit log
		auditLogRepository.save(auditLog);
	}

}
