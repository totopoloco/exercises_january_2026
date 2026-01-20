package at.mavila.exercises_january_2026.domain.number;

import java.math.BigDecimal;
import java.util.Objects;

import org.springframework.stereotype.Component;

/**
 * Domain service for calculating minimum percentage values.
 *
 * <p>
 * This component calculates the minimum additional percentage needed
 * to reach at least 100% total using the formula: Σ p − 100·(n‑1)
 * </p>
 *
 * <h2>Formula</h2>
 * <p>
 * Given an array of percentages, the minimum percentage is calculated as:
 * </p>
 *
 * <pre>
 * result = sum(percentages) - 100 * (n - 1)
 * </pre>
 * <p>
 * where n is the number of percentages.
 * </p>
 *
 * <h2>Complexity</h2>
 * <ul>
 * <li><b>Time:</b> O(n) - single pass through the array</li>
 * <li><b>Space:</b> O(1) - constant extra space</li>
 * </ul>
 *
 * @author mavila
 * @since January 2026
 */
@Component
public class MinimumPercentageCalculator {

  /**
   * Calculates the minimum additional percentage needed.
   *
   * @param numbers an array of percentages
   * @return the minimum additional percentage, or 0 if negative
   * @throws IllegalArgumentException if input array is null
   */
  public int calculate(final int[] numbers) {

    validateInput(numbers);
    BigDecimal sum = BigDecimal.ZERO;

    for (int currentNumber : numbers) {
      BigDecimal currentNumberInBigDecimal = BigDecimal.valueOf(currentNumber);
      sum = sum.add(currentNumberInBigDecimal);
    }

    return calculateExcess(sum, numbers.length);
  }

  private static int calculateExcess(BigDecimal sum, int length) {
    // Calculate the excess
    return evaluateExcess(sum.subtract(BigDecimal.valueOf(100).multiply(BigDecimal.valueOf(length - 1))));
  }

  private static int evaluateExcess(BigDecimal excess) {
    // if excess is negative return 0
    return (excess.compareTo(BigDecimal.ZERO) < 0) ? 0 : excess.intValue();
  }

  private static void validateInput(int[] numbers) {
    if (Objects.isNull(numbers)) {
      throw new IllegalArgumentException("Input array must not be null");
    }
  }

}
