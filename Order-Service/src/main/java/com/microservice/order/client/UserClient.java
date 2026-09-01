package com.microservice.order.client;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import com.microservice.order.dto.UserDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserClient {

    private final RestClient restClient;

    public UserDto getUserById(Long id) {

        try {

            return restClient.get()
                    .uri("/api/users/{id}", id)
                    .retrieve()
                    .body(UserDto.class);

        } catch (HttpClientErrorException.NotFound e) {

            throw new RuntimeException("User Not Found");

        } catch (Exception e) {

            throw new RuntimeException("User Service Unavailable");
        }
    }
}