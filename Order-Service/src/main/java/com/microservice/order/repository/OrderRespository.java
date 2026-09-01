package com.microservice.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.order.entity.Order;

public interface OrderRespository extends JpaRepository<Order, Long>{
	
	boolean existsByUserIdAndProduct(
	        Long userId,
	        String product);

}
