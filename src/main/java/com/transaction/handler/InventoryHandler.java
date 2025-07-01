package com.transaction.handler;

import org.springframework.stereotype.Service;

import com.transaction.entity.Product;
import com.transaction.repository.InventoryRepository;

@Service
public class InventoryHandler {
	private final InventoryRepository inventoryRepository;

	public InventoryHandler(InventoryRepository inventoryRepository) {
		this.inventoryRepository = inventoryRepository;
	}
	
	public Product updateProductDetails(Product product) {
		if(product.getPrice()>5000) {
			throw new RuntimeException("Database crashed !");
		}
		return inventoryRepository.save(product);
	}
	
	public Product getProduct(int id) {
		return inventoryRepository.findById(id).orElseThrow(()->new RuntimeException("Prodcut not found exception"));
	}
}
