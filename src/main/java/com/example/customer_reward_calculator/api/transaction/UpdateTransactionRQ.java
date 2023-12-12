package com.example.customer_reward_calculator.api.transaction;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateTransactionRQ {
    private Long customerId;
    private BigDecimal amount;
}
