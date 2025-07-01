package com.transaction.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.transaction.entity.Order;
import com.transaction.entity.Product;
import com.transaction.handler.AuditLogHandler;
import com.transaction.handler.InventoryHandler;
import com.transaction.handler.NotificationHandler;
import com.transaction.handler.OrderHandler;
import com.transaction.handler.PaymentValidatorHandler;
import com.transaction.handler.ProductRecommendationHandler;

@Service
public class OrderProcessingService {

	private final OrderHandler orderHandler;
	private final InventoryHandler inventoryHandler;
	private final AuditLogHandler auditLogHandler;
	private final PaymentValidatorHandler paymentValidatorHandler;
	private final NotificationHandler notificationHandler;
	private final ProductRecommendationHandler productRecommendationHandler;

	public OrderProcessingService(OrderHandler orderHandler, InventoryHandler inventoryHandler,
			AuditLogHandler auditLogHandler,PaymentValidatorHandler paymentValidatorHandler,
			NotificationHandler notificationHandler,ProductRecommendationHandler productRecommendationHandler) {
		this.orderHandler = orderHandler;
		this.inventoryHandler = inventoryHandler;
		this.auditLogHandler = auditLogHandler;
		this.paymentValidatorHandler = paymentValidatorHandler;
		this.notificationHandler = notificationHandler;
		this.productRecommendationHandler = productRecommendationHandler;
	}

	// REQIRED : join an existing transaction or create new transaction if not exist
	// REQUIRES_NEW : Always create a new Transaction , suspending if any existing
	// MANDATORY : Require an existing transaction , if nothing found it will throw exception
	// NEVER : Ensure the method will run without transaction , throw an exception if found any
	// NOT_SUPPORTED : Execute method without transaction, suspending any active transaction
	// SUPPORTS : Supports if there is any active transaction , if not execute without transaction
	// NESTED : Execute within a nested transaction, allowing nested transaction
	// to rollback independently if there is any exception without impacting outer transaction

    //outer tx
    // isolation : controls the visibility of changes made by one transaction to other transaction
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT)
	public Order placeOrder(Order order) {
		// get product form inventory
		Product product = inventoryHandler.getProduct(order.getProductId());

		// validate stock availability < (5)
		validateStockAvailability(order, product);

		// update total price
		order.setTotalPrice(order.getQuantity() * product.getPrice());
		Order saveOrder = null;
		try {
			// save order
			saveOrder = orderHandler.saveOrder(order);
			// update stock in inventory
			updateInventoryStock(order, product);
			// required_new
			auditLogHandler.logAuditDetails(order, "Order placement succeeded !");
		} catch (Exception ex) {
			// required_new
			auditLogHandler.logAuditDetails(order, "order placement failed");
			//TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 🔥 Important!
			throw new RuntimeException("order placement failed");
		}

		// This will work without transaction because we are using  @Transactional(propagation = Propagation.NEVER)
		//notificationHandler.sendOrderConfirmationNotification(order); 
		
		// This will not effect the above save and update transaction because we are using @Transactional(propagation = Propagation.MANDATORY)
		//paymentValidatorHandler.validatePayment(order); 
		//productRecommendationHandler.getRecommendations();
		getCustomerDetails();
		return saveOrder;
	}
    
	@Transactional(propagation = Propagation.SUPPORTS)
	public void getCustomerDetails() {
		System.out.println("Customer details fetched !!!!!");
	}
	// Call this method after placeAnOrder is successfully completed
    public void processOrder(Order order) {
        // Step 1: Place the order
        Order savedOrder = placeOrder(order);

        // Step 2: Send notification (non-transactional)
       // This will work without transaction because we are using  @Transactional(propagation = Propagation.NEVER)
        notificationHandler.sendOrderConfirmationNotification(order);
    }
	private static void validateStockAvailability(Order order, Product product) {
		if (order.getQuantity() > product.getStockQuantity()) {
			throw new RuntimeException("Insufficient stock !");
		}
	}

	private void updateInventoryStock(Order order, Product product) {
		int availableStock = product.getStockQuantity() - order.getQuantity();
		product.setStockQuantity(availableStock);
		inventoryHandler.updateProductDetails(product);
	}
}
