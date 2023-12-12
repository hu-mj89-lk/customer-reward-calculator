package com.example.customer_reward_calculator.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CustomerDTO {
    private Long id;
    private String name;
    private List<TransactionDTO> transactions;
}
