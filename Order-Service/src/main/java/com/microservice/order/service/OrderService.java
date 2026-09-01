package com.microservice.order.service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import com.microservice.order.client.UserClient;
import com.microservice.order.dto.CreateOrderRequest;
import com.microservice.order.dto.OrderResponse;
import com.microservice.order.dto.UserDto;
import com.microservice.order.entity.Order;
import com.microservice.order.exception.DuplicateOrderException;
import com.microservice.order.exception.OrderNotFoundException;
import com.microservice.order.exception.UserServiceUnavailableException;
import com.microservice.order.repository.OrderRespository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final UserClient userClient;
	private final OrderRespository orderRespository;   
   
	public OrderResponse createOrder(CreateOrderRequest request) {

		boolean exists =
				orderRespository.existsByUserIdAndProduct(
		                request.getUserId(),
		                request.getProduct());

		if(exists){
		    throw new DuplicateOrderException(
		            "Order Already Exists");
		}
		String correlationId = UUID.randomUUID().toString();

		System.out.println("CorrelationId : "
		        + correlationId
		        + " - Calling User Service");
		
	    UserDto user =
	            userClient.getUserById(
	                    request.getUserId(),correlationId
	            );

	    Order order = new Order();

	    order.setOrderId(System.currentTimeMillis());
	    order.setUserId(request.getUserId());
	    order.setProduct(request.getProduct());
	    order.setAmount(request.getAmount());

	    orderRespository.save(order);
	    CompletableFuture.runAsync(() -> {

	        System.out.println(
	                "OrderCreated Event Published For Order : "
	                        + order.getOrderId());

	    });

	    return new OrderResponse(
	            order.getOrderId(),
	            user.getName(),
	            order.getProduct(),
	            order.getAmount()
	    );
	}
 
    	    
//    @CircuitBreaker(
//    	    name = "userCB",
//    	    fallbackMethod = "userFallback"
//    	)
    @Retry(
    	    name = "userRetry")


    public OrderResponse getOrder(Long orderId) {
    	
    	System.out.println("Calling User Service..." +
    	        System.currentTimeMillis());

    	Order order = orderRespository
    	        .findById(orderId)
    	        .orElseThrow(() ->
    	                new OrderNotFoundException(
    	                        "Order Not Found With Id:" + orderId));
            
        

        try {
        	

        	UserDto user =
        			userClient.getUserById(
        				    order.getUserId(),
        				    UUID.randomUUID().toString()
        				);

        	return new OrderResponse(
        		    order.getOrderId(),
        		    user.getName(),
        		    order.getProduct(),
        		    order.getAmount()
        		);

        } catch (Exception e) {

            throw new UserServiceUnavailableException("User Service Down");
        }
        
    }
    public OrderResponse userFallback(
            Long orderId,
            Exception ex)
    {
        System.out.println("Fallback Executed");
        System.out.println("Circuit Breaker Activated");

        return new OrderResponse(
                orderId,
                "Circuit Breaker Activated",
                "Fallback Product",
                0
        );
    }
}
