package at.mavila.exercises_january_2026.domain.container;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WaterCalculatorTest {

    @Autowired
    private WaterCalculator waterCalculator;

    // ==================== BASIC FUNCTIONALITY TESTS ====================

    @Test
    void testMaxArea_classicExample() {
        // Classic LeetCode example
        int[] height = { 1, 8, 6, 2, 5, 4, 8, 3, 7 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(49); // indices 1 and 8: min(8,7) * 7 = 49
    }

    @Test
    void testMaxArea_descendingHeights() {
        // Descending heights - best area at the beginning
        int[] height = { 8, 7, 2, 1 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(7); // indices 0 and 1: min(8,7) * 1 = 7
    }

    // ==================== MINIMUM INPUT TESTS ====================

    @Test
    void testMaxArea_minimumArrayLength() {
        // Minimum valid input: exactly 2 elements
        int[] height = { 1, 1 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(1); // min(1,1) * 1 = 1
    }

    @Test
    void testMaxArea_twoElementsDifferentHeights() {
        int[] height = { 1, 5 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(1); // min(1,5) * 1 = 1
    }

    @Test
    void testMaxArea_twoElementsReversed() {
        int[] height = { 5, 1 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(1); // min(5,1) * 1 = 1
    }

    // ==================== EQUAL HEIGHTS TESTS ====================

    @Test
    void testMaxArea_allEqualHeights() {
        // All equal heights - max area is at the ends
        int[] height = { 5, 5, 5, 5, 5 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(20); // min(5,5) * 4 = 20
    }

    @Test
    void testMaxArea_twoEqualTallHeights() {
        // Two equal heights at the ends
        int[] height = { 10, 1, 1, 10 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(30); // min(10,10) * 3 = 30
    }

    // ==================== ASCENDING/DESCENDING PATTERNS ====================

    @Test
    void testMaxArea_ascendingHeights() {
        // Strictly ascending
        int[] height = { 1, 2, 3, 4, 5 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(6); // indices 1 and 4: min(2,5) * 3 = 6
    }

    @Test
    void testMaxArea_strictlyDescending() {
        // Strictly descending
        int[] height = { 5, 4, 3, 2, 1 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(6); // indices 0 and 3: min(5,2) * 3 = 6
    }

    // ==================== EDGE VALUE TESTS ====================

    @Test
    void testMaxArea_withZeroHeight() {
        // Contains zero height
        int[] height = { 0, 5, 0 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(0); // Any pair with 0 gives 0 area
    }

    @Test
    void testMaxArea_allZeros() {
        int[] height = { 0, 0, 0 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(0);
    }

    @Test
    void testMaxArea_zeroAtEnds() {
        int[] height = { 0, 5, 5, 0 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(5); // indices 1 and 2: min(5,5) * 1 = 5
    }

    @Test
    void testMaxArea_maxHeightValue() {
        // Maximum allowed height value (10^4)
        int[] height = { 10000, 10000 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(10000); // min(10000,10000) * 1 = 10000
    }

    // ==================== POINTER MOVEMENT TESTS ====================

    @Test
    void testMaxArea_leftPointerMoves() {
        // Left is smaller, should move left pointer
        int[] height = { 1, 10, 10 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(10); // indices 1 and 2: min(10,10) * 1 = 10
    }

    @Test
    void testMaxArea_rightPointerMoves() {
        // Right is smaller, should move right pointer
        int[] height = { 10, 10, 1 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(10); // indices 0 and 1: min(10,10) * 1 = 10
    }

    @Test
    void testMaxArea_equalHeightsMoveLeft() {
        // When equal, left moves (tests <= condition)
        int[] height = { 5, 5, 10 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(10); // indices 0 and 2: min(5,10) * 2 = 10
    }

    // ==================== SPECIAL PATTERNS ====================

    @Test
    void testMaxArea_peakInMiddle() {
        // Peak in the middle
        int[] height = { 1, 2, 10, 2, 1 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(4); // indices 0 and 4: min(1,1) * 4 = 4 or indices 1 and 3: min(2,2) * 2 = 4
    }

    @Test
    void testMaxArea_valleyInMiddle() {
        // Valley in the middle
        int[] height = { 10, 1, 1, 10 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(30); // indices 0 and 3: min(10,10) * 3 = 30
    }

    @Test
    void testMaxArea_alternating() {
        // Alternating high-low pattern
        int[] height = { 1, 10, 1, 10, 1 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(20); // indices 1 and 3: min(10,10) * 2 = 20
    }

    @Test
    void testMaxArea_largeWidthSmallHeight() {
        // Large width with small heights
        int[] height = { 1, 0, 0, 0, 0, 0, 0, 1 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(7); // indices 0 and 7: min(1,1) * 7 = 7
    }

    @Test
    void testMaxArea_smallWidthLargeHeight() {
        // Small width with large heights
        int[] height = { 100, 100 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(100);
    }

    // ==================== BOUNDARY CONDITION TESTS ====================

    @Test
    void testMaxArea_maxAreaAtStart() {
        // Maximum area found immediately at indices 0 and n-1
        int[] height = { 10, 1, 1, 1, 10 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(40); // indices 0 and 4: min(10,10) * 4 = 40
    }

    @Test
    void testMaxArea_maxAreaInMiddle() {
        // Maximum area found in the middle
        int[] height = { 1, 10, 10, 1 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(10); // indices 1 and 2: min(10,10) * 1 = 10
    }

    @Test
    void testMaxArea_threeElements() {
        int[] height = { 3, 1, 2 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(4); // indices 0 and 2: min(3,2) * 2 = 4
    }

    // ==================== VALIDATION TESTS ====================

    @Test
    void testMaxArea_nullInput_throwsException() {
        assertThatThrownBy(() -> waterCalculator.maxArea(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Input array must contain at least two heights.");
    }

    @Test
    void testMaxArea_emptyArray_throwsException() {
        int[] height = {};
        assertThatThrownBy(() -> waterCalculator.maxArea(height))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Input array must contain at least two heights.");
    }

    @Test
    void testMaxArea_singleElement_throwsException() {
        int[] height = { 5 };
        assertThatThrownBy(() -> waterCalculator.maxArea(height))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Input array must contain at least two heights.");
    }

    @Test
    void testMaxArea_exceedsMaxLength_throwsException() {
        int[] height = new int[100001]; // Exceeds 10^5
        assertThatThrownBy(() -> waterCalculator.maxArea(height))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Input array length exceeds the maximum allowed size of 10^5.");
    }

    @Test
    void testMaxArea_exactlyMaxLength_doesNotThrow() {
        int[] height = new int[100000]; // Exactly 10^5
        height[0] = 1;
        height[99999] = 1;
        // Should not throw, and should calculate area
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(99999); // min(1,1) * 99999 = 99999
    }

    // ==================== LOOP TERMINATION TESTS ====================

    @Test
    void testMaxArea_loopTerminatesCorrectly() {
        // Ensures loop terminates when left == right
        int[] height = { 2, 3, 4, 5, 18, 17, 6 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(17); // indices 0 and 6: min(2,6) * 6 = 12, indices 4 and 5: min(18,17) * 1 = 17
    }

    @Test
    void testMaxArea_multipleEqualMaxAreas() {
        // Multiple pairs with the same max area
        int[] height = { 4, 3, 2, 1, 4 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(16); // indices 0 and 4: min(4,4) * 4 = 16
    }

    // ==================== COMPARISON BOUNDARY TESTS (for mutation killing)
    // ====================

    @Test
    void testMaxArea_leftEqualsRight_movesLeft() {
        // Tests the <= condition when heights are equal
        int[] height = { 3, 3 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(3);
    }

    @Test
    void testMaxArea_leftSlightlyLessThanRight() {
        // left < right (not equal)
        int[] height = { 2, 3 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(2);
    }

    @Test
    void testMaxArea_leftSlightlyGreaterThanRight() {
        // left > right
        int[] height = { 3, 2 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(2);
    }

    @Test
    void testMaxArea_maxAreaUpdatesCorrectly() {
        // Ensure maxArea is updated when currentArea > maxArea
        int[] height = { 1, 2, 1 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(2); // indices 0 and 2: min(1,1) * 2 = 2
    }

    @Test
    void testMaxArea_maxAreaDoesNotUpdateWhenSmaller() {
        // Ensure maxArea is NOT updated when currentArea <= maxArea
        int[] height = { 5, 1, 5 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(10); // indices 0 and 2: min(5,5) * 2 = 10
    }

    // ==================== EQUIVALENT MUTATION KILLERS ====================
    // These tests target negated conditions and other mutation variants

    @Test
    void testMaxArea_loopMustExecuteAtLeastOnce() {
        // Kills: while (left >= right) - loop would never execute
        // Kills: while (left > right) - loop would never execute
        int[] height = { 1, 2 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isGreaterThan(0); // Must be > 0, not 0
        assertThat(result).isEqualTo(1);
    }

    @Test
    void testMaxArea_loopMustIterateMultipleTimes() {
        // Kills mutations that exit loop early
        int[] height = { 1, 1, 1, 1, 1 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(4); // Need full iteration to find max width
    }

    @Test
    void testMaxArea_bothPointersMustMove() {
        // Left must move when leftHeight < rightHeight
        // Right must move when rightHeight < leftHeight
        int[] height = { 1, 5, 5, 1 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(5); // indices 1,2: min(5,5)*1=5
    }

    @Test
    void testMaxArea_leftPointerMustIncrement() {
        // Kills: left++ removed or changed to left--
        int[] height = { 1, 10, 10, 10 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(20); // indices 1,3: min(10,10)*2=20
    }

    @Test
    void testMaxArea_rightPointerMustDecrement() {
        // Kills: right-- removed or changed to right++
        int[] height = { 10, 10, 10, 1 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(20); // indices 0,2: min(10,10)*2=20
    }

    @Test
    void testMaxArea_conditionMustUseCorrectComparison() {
        // Kills: if condition completely negated (>= instead of <=)
        // When left=1, right=8: need to move correctly based on heights
        int[] height = { 2, 8, 8, 2 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(8); // indices 1,2: min(8,8)*1=8
    }

    @Test
    void testMaxArea_mustUseMinNotMax() {
        // Kills: Math.min changed to Math.max
        int[] height = { 1, 100 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(1); // min(1,100)*1=1, NOT max(1,100)*1=100
    }

    @Test
    void testMaxArea_mustUseMaxForArea() {
        // Kills: Math.max changed to Math.min for maxArea update
        int[] height = { 5, 1, 1, 1, 5 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(20); // First area is best: min(5,5)*4=20
    }

    @Test
    void testMaxArea_widthCalculationCorrect() {
        // Kills: (right - left) changed to (left - right) or other mutations
        int[] height = { 3, 3 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(3); // width must be 1, not -1 or 0
    }

    @Test
    void testMaxArea_increasingMaxAreaOverIterations() {
        // Test where max area increases during iteration
        int[] height = { 1, 2, 4, 3 };
        int result = waterCalculator.maxArea(height);
        assertThat(result).isEqualTo(4); // indices 1,3: min(2,3)*2=4
    }
}
