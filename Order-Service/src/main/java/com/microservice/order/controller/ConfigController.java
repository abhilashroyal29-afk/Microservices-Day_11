package com.microservice.order.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order-config")
public class ConfigController {
	
	@Value("${app.message}")
	private String message;
	
	@GetMapping("/message")
	public String message() {
		return message;
	}

}

