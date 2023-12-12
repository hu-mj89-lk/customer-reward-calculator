package com.example.customer_reward_calculator.api.transaction;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteTransactionRS {
    private Long id;
    private Boolean status;
}
