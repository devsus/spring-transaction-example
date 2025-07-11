package com.transaction.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.transaction.entity.Product;

@Service
public class ProductRecommendationHandler {

	//Fetch product recommendations
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Product> getRecommendations(){
		//Simulate hardcoded product recommendations 
		List<Product> recommendations = new ArrayList<>();
		recommendations.add(new Product(101, "Wireless Headphones", 99.99, 50));
        recommendations.add(new Product(102, "Smartphone Case", 19.99, 200));
        recommendations.add(new Product(103, "Bluetooth Speaker", 49.99, 75));
        recommendations.add(new Product(104, "Gaming Mouse", 59.99, 100));

        System.out.println("Recommendations fetched for customer ");
        return recommendations;
	}
}
