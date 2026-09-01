package com.microservice.payment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.payment.dto.PaymentResponse;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
	
	@GetMapping("/{orderId}")
	public PaymentResponse processPayment(@PathVariable Long orderId) 
		throws InterruptedException{
			Thread.sleep(10000);
		
		return new PaymentResponse(orderId,"SUCCESS");
		}

}
