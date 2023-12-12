package com.example.customer_reward_calculator.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@EqualsAndHashCode(callSuper = true)
public class UnprocessableEntityException extends RuntimeException {
    private final HttpStatus httpStatus;

    public UnprocessableEntityException(String message) {
        super(message);
        httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
    }
}
