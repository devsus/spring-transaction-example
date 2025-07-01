package com.transaction.handler;

import org.springframework.stereotype.Service;

import com.transaction.entity.Order;

@Service
public class NotificationHandler {
	public void sendOrderConfirmationNotification(Order order) {
		// Send an email notification to the customer
        System.out.println( order.getId()+" Order placed successfully");
	}
}
