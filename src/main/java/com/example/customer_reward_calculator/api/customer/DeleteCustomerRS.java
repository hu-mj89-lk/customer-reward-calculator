package com.example.customer_reward_calculator.api.customer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteCustomerRS {
    private Long id;
    private Boolean status;
}
