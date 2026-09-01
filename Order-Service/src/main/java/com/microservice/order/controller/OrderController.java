package com.microservice.order.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.order.client.UserClient;
import com.microservice.order.dto.CreateOrderRequest;
import com.microservice.order.dto.OrderResponse;
import com.microservice.order.dto.PaymentResponse;
import com.microservice.order.dto.UserDto;
import com.microservice.order.service.OrderService;
import com.microservice.order.service.PaymentClient;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
	
	private final OrderService orderService;
	@Autowired
	private PaymentClient paymentClient;
	private final UserClient userClient;
	@PostMapping
	public ResponseEntity<OrderResponse> createOrder(
	        @Valid @RequestBody CreateOrderRequest request) {

	    return ResponseEntity.ok(
	            orderService.createOrder(request));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<OrderResponse> getOrder(@PathVariable Long id) {
		
				return ResponseEntity.ok(orderService.getOrder(id));
	}
	@GetMapping("/{id}/payment")
	public PaymentResponse getPayment(@PathVariable Long id) {
		
		orderService.getOrder(id);
		return paymentClient.makePayment(id);
		
	}
	@GetMapping("/{id}/user")
	public String getOrderUser(@PathVariable Long id) {
		UserDto user = userClient.getUserById(id);
		return "User Name : "+ user.getName();
		
		
	}

}
