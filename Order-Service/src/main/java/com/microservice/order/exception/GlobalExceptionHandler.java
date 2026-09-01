package com.microservice.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.microservice.order.dto.ApiError;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            UserServiceUnavailableException.class)
    public ResponseEntity<String> handleException(
            UserServiceUnavailableException ex) {

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(ex.getMessage());
    }
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ApiError> handleOrderNotFound(
            OrderNotFoundException ex) {
    	ApiError error = new ApiError(404,"Order_NOT_FOUND",ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

        @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<String>handleRuntimeException(RuntimeException ex){
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        	
        
    }
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiError> handleValidation(
                MethodArgumentNotValidException ex){

            ApiError error = new ApiError(
                    400,
                    "VALIDATION_FAILED",
                    "Invalid Request Data");

            return ResponseEntity.badRequest().body(error);
        }
        @ExceptionHandler(DuplicateOrderException.class)
        public ResponseEntity<ApiError> handleDuplicateOrder(
                DuplicateOrderException ex) {

            ApiError error = new ApiError(
                    409,
                    "DUPLICATE_ORDER",
                    ex.getMessage());

            return ResponseEntity.status(409).body(error);
        }
}