package com.microservice.user.dto;

import lombok.Data;

@Data
public class UserResponse {
	Long id;
	String name;
	String email;

}
