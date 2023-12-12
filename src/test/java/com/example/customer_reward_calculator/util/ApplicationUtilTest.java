package com.example.customer_reward_calculator.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ApplicationUtilTest {

    @Test
    void calculatePoints() {
        BigDecimal given = new BigDecimal(120);

        BigDecimal points = ApplicationUtil.calculatePoints(given);

        assertThat(points).isEqualTo(new BigDecimal(90));
    }

}
