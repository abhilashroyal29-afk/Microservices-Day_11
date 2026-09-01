package com.microservice.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.user.dto.UserDto;
import com.microservice.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getUserById(
	        @PathVariable Long id,
	        @RequestHeader("X-Correlation-ID") String correlationId)
	        throws Exception {

	    System.out.println(
	            "Received CorrelationId : "
	            + correlationId);

	    return ResponseEntity.ok(userService.getById(id));
	}

}
