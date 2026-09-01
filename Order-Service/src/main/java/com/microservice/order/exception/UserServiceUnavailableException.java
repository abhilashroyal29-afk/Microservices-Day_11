package com.microservice.order.exception;

public class UserServiceUnavailableException extends RuntimeException{
	
	public UserServiceUnavailableException(String message) {
		super(message);
	}

}
