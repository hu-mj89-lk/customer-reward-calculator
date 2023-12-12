package com.example.customer_reward_calculator.api.customer;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
public class GetCustomerRS {
    private Long id;
    private String name;
    private List<TransactionRS> transactions;

    @Data
    @Builder
    public static class TransactionRS {
        private Long id;
        private BigDecimal amount;
        private OffsetDateTime dateTime;
        private BigDecimal points;
    }
}
