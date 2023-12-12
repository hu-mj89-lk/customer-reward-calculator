package com.example.customer_reward_calculator.api.customer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCustomerRS {
    private Long id;
    private String name;
}
