package com.transaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transaction.entity.Product;

public interface InventoryRepository extends JpaRepository<Product, Integer>{

}
