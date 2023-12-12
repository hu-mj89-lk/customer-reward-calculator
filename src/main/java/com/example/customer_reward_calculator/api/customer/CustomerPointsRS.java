package com.example.customer_reward_calculator.api.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPointsRS {
    private Long id;
    private String name;
    private List<PointRS> monthlyPoints;
    private BigDecimal totalPoints;

    @Data
    @Builder
    public static class PointRS {
        private Integer month;
        private BigDecimal points;
    }
}
