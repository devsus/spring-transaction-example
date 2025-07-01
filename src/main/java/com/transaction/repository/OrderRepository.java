package com.transaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transaction.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>{

}
