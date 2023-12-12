package com.example.customer_reward_calculator.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
public class TransactionDTO {
    private Long id;
    private Long customerId;
    private BigDecimal amount;
    private OffsetDateTime dateTime;
    private BigDecimal points;
}
