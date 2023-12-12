package com.example.customer_reward_calculator.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CustomerPointsDTO {
    private Long id;
    private String name;
    private List<PointsDTO> monthlyPoints;
    private BigDecimal totalPoints;

    @Data
    @Builder
    public static class PointsDTO {
        private Integer month;
        private BigDecimal points;
    }
}
