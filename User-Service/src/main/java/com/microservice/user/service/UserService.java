package com.microservice.user.service;

import org.springframework.stereotype.Service;

import com.microservice.user.dto.UserDto;
import com.microservice.user.entity.User;
import com.microservice.user.exception.UserNotFoundException;
import com.microservice.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto getById(Long id) {
    	System.out.println("User Service Called..");
    	System.out.println("User Id : "+id);

        User user = userRepository
                .findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User Not Found"));

        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}

