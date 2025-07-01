package com.transaction.handler;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.transaction.entity.AuditLog;
import com.transaction.entity.Order;
import com.transaction.repository.AuditLogRepository;

@Service
public class PaymentValidatorHandler {

	@Autowired
	private AuditLogRepository auditLogRepository;
	
	//@Transactional(propagation = Propagation.MANDATORY)
	@Transactional(propagation = Propagation.NESTED) // NESTED means child transaction
	public void validatePayment(Order order) {
		//Assume payment processing happens here
		boolean paymentSuccessfull = false;
		
		// if payment is unsuccessful, we log the payment failure in the mandatory transaction 
		if(!paymentSuccessfull) {
			AuditLog paymentFailureLog = new AuditLog();
			paymentFailureLog.setOrderId(Long.valueOf(order.getId()));
			paymentFailureLog.setAction("Payment failed for Order !");
			paymentFailureLog.setTimestamp(LocalDateTime.now());
			
//			if(order.getTotalPrice()>1000) {
//				throw new RuntimeException("Error in payment validator !");
//			}
			//save the payment failure log
			auditLogRepository.save(paymentFailureLog);
		}
	}
}
