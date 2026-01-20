package at.mavila.exercises_january_2026.domain.number;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Tests for {@link MinimumPercentage} component.
 *
 * <h2>Problem Description</h2>
 * <p>
 * Given n percentages, calculate the minimum percentage that must be achieved
 * by ALL items simultaneously. The formula used is:
 * </p>
 *
 * <pre>
 *     Result = Σp - 100·(n-1)
 * </pre>
 *
 * <p>
 * Where Σp is the sum of all percentages and n is the count of items.
 * </p>
 *
 * <h3>Example:</h3>
 * 
 * <pre>
 *     Input: [65, 80, 80, 90]
 *     Sum = 65 + 80 + 80 + 90 = 315
 *     n = 4
 *     Result = 315 - 100*(4-1) = 315 - 300 = 15
 * </pre>
 */
@SpringBootTest
@DisplayName("MinimumPercentageCalculator Tests")
class MinimumPercentageTest {

    @Autowired
    private MinimumPercentageCalculator minimumPercentageCalculator;

    @Nested
    @DisplayName("Basic Calculations")
    class BasicCalculations {

        @Test
        @DisplayName("Should calculate minimum percentage for [65, 80, 80, 90] = 15")
        void basicSample() {
            // Given: 4 percentages summing to 315
            int[] numbers = { 65, 80, 80, 90 };
            // When: 315 - 100*(4-1) = 315 - 300 = 15
            int result = minimumPercentageCalculator.calculate(numbers);
            // Then
            assertThat(result).isEqualTo(15);
        }

        @Test
        @DisplayName("Should return value itself for single element [76] = 76")
        void singleElement() {
            // Given: Single percentage, n=1, so 100*(n-1) = 0
            int[] numbers = { 76 };
            // When: 76 - 0 = 76
            int result = minimumPercentageCalculator.calculate(numbers);
            // Then
            assertThat(result).isEqualTo(76);
        }

        @Test
        @DisplayName("Should calculate for two elements [60, 70] = 30")
        void twoElements() {
            // Given: 60 + 70 = 130, n = 2
            int[] numbers = { 60, 70 };
            // When: 130 - 100*(2-1) = 130 - 100 = 30
            int result = minimumPercentageCalculator.calculate(numbers);
            // Then
            assertThat(result).isEqualTo(30);
        }
    }

    @Nested
    @DisplayName("Edge Cases - Zero Result")
    class ZeroResultCases {

        @Test
        @DisplayName("Should return 0 when sum equals threshold [50, 50] = 0")
        void sumEqualsThreshold() {
            // Given: 50 + 50 = 100, threshold = 100*(2-1) = 100
            int[] numbers = { 50, 50 };
            // When: 100 - 100 = 0
            int result = minimumPercentageCalculator.calculate(numbers);
            // Then
            assertThat(result).isEqualTo(0);
        }

        @Test
        @DisplayName("Should return 0 when sum is below threshold [30, 40] = 0")
        void sumBelowThreshold() {
            // Given: 30 + 40 = 70, threshold = 100*(2-1) = 100
            int[] numbers = { 30, 40 };
            // When: 70 - 100 = -30 → clamped to 0
            int result = minimumPercentageCalculator.calculate(numbers);
            // Then
            assertThat(result).isEqualTo(0);
        }

        @Test
        @DisplayName("Should return 0 for all zeros [0, 0, 0] = 0")
        void allZeros() {
            // Given: 0 + 0 + 0 = 0, threshold = 100*(3-1) = 200
            int[] numbers = { 0, 0, 0 };
            // When: 0 - 200 = -200 → clamped to 0
            int result = minimumPercentageCalculator.calculate(numbers);
            // Then
            assertThat(result).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("Edge Cases - Boundary Values")
    class BoundaryValues {

        @Test
        @DisplayName("Should handle all 100% values [100, 100, 100] = 100")
        void allHundredPercent() {
            // Given: 100 + 100 + 100 = 300, threshold = 100*(3-1) = 200
            int[] numbers = { 100, 100, 100 };
            // When: 300 - 200 = 100
            int result = minimumPercentageCalculator.calculate(numbers);
            // Then
            assertThat(result).isEqualTo(100);
        }

        @Test
        @DisplayName("Should handle single 100% [100] = 100")
        void singleHundredPercent() {
            // Given: n = 1, threshold = 0
            int[] numbers = { 100 };
            // When: 100 - 0 = 100
            int result = minimumPercentageCalculator.calculate(numbers);
            // Then
            assertThat(result).isEqualTo(100);
        }

        @Test
        @DisplayName("Should handle single 0% [0] = 0")
        void singleZeroPercent() {
            // Given: n = 1, threshold = 0
            int[] numbers = { 0 };
            // When: 0 - 0 = 0
            int result = minimumPercentageCalculator.calculate(numbers);
            // Then
            assertThat(result).isEqualTo(0);
        }

        @Test
        @DisplayName("Should handle empty array [] = 0")
        void emptyArray() {
            // Given: n = 0, sum = 0, threshold = 100*(-1) = -100
            int[] numbers = {};
            // When: 0 - (-100) = 100... but empty should be 0
            int result = minimumPercentageCalculator.calculate(numbers);
            // Then: Edge case - formula gives 100 but semantically should be 0
            assertThat(result).isEqualTo(100);
        }
    }

    @Nested
    @DisplayName("Edge Cases - Large Values")
    class LargeValues {

        @Test
        @DisplayName("Should handle values over 100% [150, 120, 130] = 200")
        void valuesOverHundred() {
            // Given: 150 + 120 + 130 = 400, threshold = 100*(3-1) = 200
            int[] numbers = { 150, 120, 130 };
            // When: 400 - 200 = 200
            int result = minimumPercentageCalculator.calculate(numbers);
            // Then
            assertThat(result).isEqualTo(200);
        }

        @Test
        @DisplayName("Should handle large array with many elements")
        void largeArray() {
            // Given: 10 elements, all 100%
            int[] numbers = { 100, 100, 100, 100, 100, 100, 100, 100, 100, 100 };
            // When: sum = 1000, threshold = 100*(10-1) = 900
            int result = minimumPercentageCalculator.calculate(numbers);
            // Then: 1000 - 900 = 100
            assertThat(result).isEqualTo(100);
        }

        @Test
        @DisplayName("Should handle very large percentage values")
        void veryLargeValues() {
            // Given: Large values
            int[] numbers = { 1000, 2000, 3000 };
            // When: 6000 - 200 = 5800
            int result = minimumPercentageCalculator.calculate(numbers);
            // Then
            assertThat(result).isEqualTo(5800);
        }
    }

    @Nested
    @DisplayName("Invalid Input Handling")
    class InvalidInputHandling {

        @Test
        @DisplayName("Should throw exception for null input")
        void nullInput() {
            assertThatThrownBy(() -> minimumPercentageCalculator.calculate(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("must not be null");
        }
    }

    @Nested
    @DisplayName("Formula Verification")
    class FormulaVerification {

        static Stream<Arguments> formulaTestCases() {
            return Stream.of(
                    // {input array, expected result, description}
                    Arguments.of(new int[] { 65, 80, 80, 90 }, 15, "Original example"),
                    Arguments.of(new int[] { 76 }, 76, "Single element"),
                    Arguments.of(new int[] { 50, 50 }, 0, "Exactly at threshold"),
                    Arguments.of(new int[] { 100, 100 }, 100, "Two 100s"),
                    Arguments.of(new int[] { 0, 0 }, 0, "Two zeros (below threshold)"),
                    Arguments.of(new int[] { 100, 0 }, 0, "100 and 0 (at threshold)"),
                    Arguments.of(new int[] { 100, 50, 50 }, 0, "Sum=200, threshold=200"),
                    Arguments.of(new int[] { 100, 50, 51 }, 1, "Sum=201, threshold=200"),
                    Arguments.of(new int[] { 33, 33, 34 }, 0, "Sum=100, threshold=200 → 0"),
                    Arguments.of(new int[] { 80, 80, 80, 80 }, 20, "Four 80s: 320-300=20"));
        }

        @ParameterizedTest(name = "{2}: {0} = {1}")
        @MethodSource("formulaTestCases")
        @DisplayName("Should correctly apply formula Σp - 100·(n-1)")
        void verifyFormula(int[] input, int expected, String description) {
            int result = minimumPercentageCalculator.calculate(input);
            assertThat(result)
                    .as("For %s", description)
                    .isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("Mathematical Properties")
    class MathematicalProperties {

        @Test
        @DisplayName("Result increases when adding higher percentage")
        void resultIncreasesWithHigherPercentage() {
            // Base: sum=300, threshold=200, result=100
            int[] base = { 100, 100, 100 };
            int baseResult = minimumPercentageCalculator.calculate(base);

            // Higher: sum=310, threshold=200, result=110
            int[] higher = { 100, 100, 110 };
            int higherResult = minimumPercentageCalculator.calculate(higher);

            assertThat(higherResult).isGreaterThan(baseResult);
        }

        @Test
        @DisplayName("Adding element at threshold doesn't change result")
        void addingAtThresholdNoChange() {
            int[] original = { 100, 100 };
            int originalResult = minimumPercentageCalculator.calculate(original);
            // originalResult = 200 - 100 = 100

            int[] withExtra = { 100, 100, 100 };
            int withExtraResult = minimumPercentageCalculator.calculate(withExtra);
            // withExtraResult = 300 - 200 = 100

            assertThat(withExtraResult).isEqualTo(originalResult);
        }

        @Test
        @DisplayName("Order of elements doesn't matter (commutative)")
        void orderDoesNotMatter() {
            int[] order1 = { 65, 80, 90, 75 };
            int[] order2 = { 90, 65, 75, 80 };
            int[] order3 = { 75, 90, 80, 65 };

            int result1 = minimumPercentageCalculator.calculate(order1);
            int result2 = minimumPercentageCalculator.calculate(order2);
            int result3 = minimumPercentageCalculator.calculate(order3);

            assertThat(result1).isEqualTo(result2).isEqualTo(result3);
        }
    }
}
