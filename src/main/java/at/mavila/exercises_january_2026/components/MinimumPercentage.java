package at.mavila.exercises_january_2026.components;

import java.math.BigDecimal;
import java.util.Objects;

import org.springframework.stereotype.Component;

@Component
public class MinimumPercentage {

    /**
     * It simply applies the formula: Σ p − 100·(n‑1)
     * where p is each percentage in the input array and n is the number of
     * percentages.
     *
     * @param numbers an array of percentages
     * @return the minimum additional percentage needed to reach at least 100% total
     */
    public int calculate(final int[] numbers) {

        if (Objects.isNull(numbers)) {
            throw new IllegalArgumentException("Input array must not be null");
        }
        int n = numbers.length;

        BigDecimal sum = BigDecimal.ZERO;

        for (int i = 0; i < n; i++) {
            int currentNumber = numbers[i];
            BigDecimal currentNumberInBigDecimal = BigDecimal.valueOf(currentNumber);
            sum = sum.add(currentNumberInBigDecimal);
        }

        // Calculate the excess
        BigDecimal excess = sum.subtract(BigDecimal.valueOf(100).multiply(BigDecimal.valueOf(n - 1)));

        // if excess is negative return 0
        if (excess.compareTo(BigDecimal.ZERO) < 0) {
            return 0;
        }

        return excess.intValue();
    }

}
