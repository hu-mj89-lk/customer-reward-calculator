package com.example.customer_reward_calculator.api.error;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorRS {
    private Integer status;
    private String message;
}
