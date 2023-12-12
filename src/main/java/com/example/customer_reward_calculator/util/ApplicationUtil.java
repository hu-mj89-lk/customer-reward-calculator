package com.example.customer_reward_calculator.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApplicationUtil {

    private static final BigDecimal FIFTY = new BigDecimal(50);
    private static final BigDecimal HUNDRED = new BigDecimal(100);

    /**
     * Calculates points for a given amount.
     * <br><br>
     * <b>Logic</b> : 2 points for every dollar spent over $100 in each transaction,
     * plus 1 point for every dollar spent over $50 in each transaction
     * <br><br>
     * (e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points)
     * <br>
     *
     * @param amount given amount
     * @return points
     */
    public static BigDecimal calculatePoints(BigDecimal amount) {
        BigDecimal remaining = BigDecimal.ZERO.add(amount);
        BigDecimal points = new BigDecimal(0);

        if (remaining.compareTo(HUNDRED) > 0) {
            points = points.add(remaining.subtract(HUNDRED).multiply(new BigDecimal(2)));
            remaining = HUNDRED;
        }
        if (remaining.compareTo(FIFTY) > 0) {
            points = points.add(remaining.subtract(FIFTY).multiply(new BigDecimal(1)));
        }

        return points;
    }

}
