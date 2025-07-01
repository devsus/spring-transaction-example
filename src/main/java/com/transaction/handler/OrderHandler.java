package com.transaction.handler;

import org.springframework.stereotype.Service;

import com.transaction.entity.Order;
import com.transaction.repository.OrderRepository;

@Service
public class OrderHandler {

	private final OrderRepository orderRepository;
	
	
	public OrderHandler(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	
	public Order saveOrder(Order order) {
		return orderRepository.save(order);
	}

}
