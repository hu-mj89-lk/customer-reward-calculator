package com.example.customer_reward_calculator.api.transaction;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
public class CreateTransactionRS {
    private Long id;
    private Long customerId;
    private BigDecimal amount;
    private OffsetDateTime dateTime;
    private BigDecimal points;
}
