package com.microservice.order;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.microservice.order.dto.CreateOrderRequest;
import com.microservice.order.dto.OrderResponse;
import com.microservice.order.exception.DuplicateOrderException;
import com.microservice.order.exception.OrderNotFoundException;
import com.microservice.order.service.OrderService;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class OrderServiceApplicationTests {
	@Autowired
	private OrderService orderService;

	@Test
	void createOrderSuccess() {
		CreateOrderRequest request = new CreateOrderRequest();
		request.setUserId(10L);
		request.setProduct("Mobile");
		request.setAmount(30000.0);
		
		OrderResponse order = orderService.createOrder(request);
		
		assertNotNull(order);
		
	}
	@Test
	void duplicateOrderTest() {

	    CreateOrderRequest request = new CreateOrderRequest();

	    request.setUserId(999L);
	    request.setProduct("TestLaptop");
	    request.setAmount(50000.0);

	    orderService.createOrder(request);

	    assertThrows(
	            DuplicateOrderException.class,
	            () -> orderService.createOrder(request)
	    );
	}
	@Test
	void orderNotFoundTest() {
		assertThrows(OrderNotFoundException.class,
				()-> orderService.getOrder(999L));
	}

}
