package com.microservice.order.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservice.order.dto.PaymentResponse;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;


@Service
public class PaymentClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${payment.service.url}")
    private String paymentUrl;
    @Retryable(maxAttempts = 3)
  
    @CircuitBreaker(
        name = "paymentCB",
         fallbackMethod = "fallbackPayment")
    
    
    public PaymentResponse makePayment(Long orderId) {
    
    	System.out.println("Calling Payment Service :"+System.currentTimeMillis());

        return restTemplate.getForObject(
                paymentUrl + "/api/payments/" + orderId,
                PaymentResponse.class);
    }

    public PaymentResponse fallbackPayment(
            Long orderId,
            Exception ex) {
    	System.out.println("FallBack Exceuted");

        return new PaymentResponse(
                orderId,
                "Circuit Breaker Activated");
    }
}
	


