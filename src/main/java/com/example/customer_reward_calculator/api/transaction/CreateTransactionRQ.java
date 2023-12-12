package com.example.customer_reward_calculator.api.transaction;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateTransactionRQ {
    private Long customerId;
    private BigDecimal amount;
}
