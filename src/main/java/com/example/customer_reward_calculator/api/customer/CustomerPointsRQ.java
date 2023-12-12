package com.example.customer_reward_calculator.api.customer;

import lombok.Data;

import java.util.List;

@Data
public class CustomerPointsRQ {
    private Long customerId;
    private Integer year;
    private List<Integer> months;
}
