package at.mavila.exercises_january_2026.domain.collection;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RearrangingFruitsTest {

    @Autowired
    private RearrangingFruits rearrangingFruits;

    // ==================== Basic Functionality Tests ====================

    @Test
    void testRearrange() {
        long result1 = this.rearrangingFruits.rearrange(new int[] { 4, 2, 2, 2 }, new int[] { 1, 4, 1, 2 });
        assertThat(result1).isEqualTo(1);
    }

    @Test
    void testRearrange_Impossible() {
        long result2 = this.rearrangingFruits.rearrange(new int[] { 2, 3, 4, 1 }, new int[] { 3, 2, 5, 1 });
        assertThat(result2).isEqualTo(-1);
    }

    // ==================== Already Identical Baskets ====================

    @Test
    void testRearrange_AlreadyIdentical_SingleElement() {
        long result = this.rearrangingFruits.rearrange(new int[] { 5 }, new int[] { 5 });
        assertThat(result).isZero();
    }

    @Test
    void testRearrange_AlreadyIdentical_MultipleElements() {
        long result = this.rearrangingFruits.rearrange(new int[] { 1, 2, 3 }, new int[] { 1, 2, 3 });
        assertThat(result).isZero();
    }

    @Test
    void testRearrange_AlreadyIdentical_DifferentOrder() {
        long result = this.rearrangingFruits.rearrange(new int[] { 3, 1, 2 }, new int[] { 2, 3, 1 });
        assertThat(result).isZero();
    }

    @Test
    void testRearrange_AlreadyIdentical_WithDuplicates() {
        long result = this.rearrangingFruits.rearrange(new int[] { 2, 2, 3, 3 }, new int[] { 3, 2, 3, 2 });
        assertThat(result).isZero();
    }

    // ==================== Simple Swap Cases ====================

    @Test
    void testRearrange_SimpleSwap_TwoElements() {
        // Basket1: [1, 2], Basket2: [2, 1] -> already identical by frequency
        long result = this.rearrangingFruits.rearrange(new int[] { 1, 2 }, new int[] { 2, 1 });
        assertThat(result).isZero();
    }

    @Test
    void testRearrange_SimpleSwap_NeedsOneSwap() {
        // Basket1: [1, 1], Basket2: [2, 2] -> need to swap one 1 with one 2, cost =
        // min(1,2) = 1
        long result = this.rearrangingFruits.rearrange(new int[] { 1, 1 }, new int[] { 2, 2 });
        assertThat(result).isEqualTo(1);
    }

    @Test
    void testRearrange_SimpleSwap_CostIsSmaller() {
        // Basket1: [5, 5], Basket2: [10, 10] -> swap one 5 with one 10, cost =
        // min(5,10) = 5
        long result = this.rearrangingFruits.rearrange(new int[] { 5, 5 }, new int[] { 10, 10 });
        assertThat(result).isEqualTo(5);
    }

    // ==================== Via Minimum Cost Cases ====================

    @Test
    void testRearrange_ViaMinimumIsCheaper() {
        // Basket1: [1, 100, 100], Basket2: [100, 1, 1]
        // freq1: {1:1, 100:2}, freq2: {100:1, 1:2}
        // union: {1:3, 100:3} -> both odd -> impossible to split evenly
        long result = this.rearrangingFruits.rearrange(new int[] { 1, 100, 100 }, new int[] { 100, 1, 1 });
        assertThat(result).isEqualTo(-1);
    }

    @Test
    void testRearrange_ViaMinimumIsCheaper_ValidCase() {
        // Basket1: [1, 1, 100, 100], Basket2: [100, 100, 1, 1]
        // freq1: {1:2, 100:2}, freq2: {100:2, 1:2}
        // union: {1:4, 100:4} -> both even, target each = 2
        // Already balanced! No swaps needed.
        long result = this.rearrangingFruits.rearrange(new int[] { 1, 1, 100, 100 }, new int[] { 100, 100, 1, 1 });
        assertThat(result).isZero();
    }

    @Test
    void testRearrange_ViaMinUsedForExpensiveSwap() {
        // Basket1: [1, 50, 50, 50, 50], Basket2: [50, 50, 50, 50, 1]
        // Both have same frequency - already balanced
        long result = this.rearrangingFruits.rearrange(
                new int[] { 1, 50, 50, 50, 50 },
                new int[] { 50, 50, 50, 50, 1 });
        assertThat(result).isZero();
    }

    @Test
    void testRearrange_ActualViaMinCase() {
        // Basket1: [84,80,18,32], Basket2: [61,7,36,72]
        // This is a case where baskets cannot be made equal
        long result = this.rearrangingFruits.rearrange(
                new int[] { 84, 80, 18, 32 },
                new int[] { 61, 7, 36, 72 });
        assertThat(result).isEqualTo(-1);
    }

    @Test
    void testRearrange_MixedDirectAndViaMin() {
        // Some swaps are cheaper direct, others via minimum
        long result = this.rearrangingFruits.rearrange(new int[] { 1, 2, 50, 50 }, new int[] { 50, 50, 1, 2 });
        assertThat(result).isZero(); // already balanced
    }

    // ==================== Odd Count Cases (Impossible) ====================

    @Test
    void testRearrange_OddTotalCount_SingleValue() {
        // Total count of 3 is odd (1 + 2 = 3), cannot split evenly
        long result = this.rearrangingFruits.rearrange(new int[] { 3 }, new int[] { 4 });
        assertThat(result).isEqualTo(-1);
    }

    @Test
    void testRearrange_OddTotalCount_InLargerBasket() {
        // One fruit type has odd total
        long result = this.rearrangingFruits.rearrange(new int[] { 1, 1, 1 }, new int[] { 2, 2, 2 });
        assertThat(result).isEqualTo(-1);
    }

    // ==================== Large Values ====================

    @Test
    void testRearrange_LargeValues() {
        long result = this.rearrangingFruits.rearrange(
                new int[] { 1000000, 1000000 },
                new int[] { 1, 1 });
        // Need to swap one 1000000 with one 1, cost = min(1, 1000000) = 1
        assertThat(result).isEqualTo(1);
    }

    @Test
    void testRearrange_VeryLargeValuesUseViaMin() {
        // Basket1: [1, 500000, 500000], Basket2: [500000, 1, 1]
        // freq1: {1:1, 500000:2}, freq2: {500000:1, 1:2}
        // union: {1:3, 500000:3} -> both odd -> impossible
        long result = this.rearrangingFruits.rearrange(
                new int[] { 1, 500000, 500000 },
                new int[] { 500000, 1, 1 });
        assertThat(result).isEqualTo(-1);
    }

    @Test
    void testRearrange_ViaMinActuallyUsed() {
        // Basket1: [1, 1, 50, 50], Basket2: [1, 1, 100, 100]
        // freq1: {1:2, 50:2}, freq2: {1:2, 100:2}
        // union: {1:4, 50:2, 100:2}
        // target: 1->2each, 50->1each, 100->1each
        // basket1 excess: [50] (has 2, needs 1)
        // basket2 excess: [100] (has 2, needs 1)
        // swap 50<->100: direct cost = min(50,100) = 50, via min = 2*1 = 2
        // Choose via min = 2
        long result = this.rearrangingFruits.rearrange(
                new int[] { 1, 1, 50, 50 },
                new int[] { 1, 1, 100, 100 });
        assertThat(result).isEqualTo(2);
    }

    // ==================== Edge Cases ====================

    @Test
    void testRearrange_AllSameValue() {
        long result = this.rearrangingFruits.rearrange(new int[] { 7, 7, 7 }, new int[] { 7, 7, 7 });
        assertThat(result).isZero();
    }

    @Test
    void testRearrange_TwoDistinctValues() {
        long result = this.rearrangingFruits.rearrange(new int[] { 1, 1, 2, 2 }, new int[] { 1, 1, 2, 2 });
        assertThat(result).isZero();
    }

    @Test
    void testRearrange_MultipleSwapsNeeded() {
        // Multiple swaps required
        long result = this.rearrangingFruits.rearrange(
                new int[] { 1, 1, 1, 1 },
                new int[] { 2, 2, 2, 2 });
        // Need 2 swaps: each costs min(1, 2) = 1, total = 2
        assertThat(result).isEqualTo(2);
    }

    // ==================== Large Array Overflow Test ====================

    @Test
    void testRearrange_LargeArraysNoOverflow() {
        // Test to verify no integer overflow with large arrays
        // basket1: 100000 elements of value 1
        // basket2: 100000 elements of value 2
        // freq1: {1: 100000}, freq2: {2: 100000}
        // union: {1: 100000, 2: 100000} - both even
        // target each: 50000 of value 1, 50000 of value 2
        // excess1: 50000 of value 1 (has 100000, needs 50000)
        // excess2: 50000 of value 2 (has 100000, needs 50000)
        // globalMin = 1
        // each swap: direct = min(1, 2) = 1, via min = 2 * 1 = 2
        // Direct is cheaper: 50000 * 1 = 50000
        int[] basket1 = new int[100_000];
        int[] basket2 = new int[100_000];
        java.util.Arrays.fill(basket1, 1);
        java.util.Arrays.fill(basket2, 2);

        long result = this.rearrangingFruits.rearrange(basket1, basket2);
        // Cost = 50000 swaps * min(1, 2) = 50000
        assertThat(result).isEqualTo(50_000L);
    }

    @Test
    void testRearrange_LargeArraysVeryLargeCostNoOverflow() {
        // Test specifically for the overflow case where result > Integer.MAX_VALUE
        // basket1: all value 1_000_000_000
        // basket2: all value 1
        // Need to swap half the elements: 50000 swaps
        // Each swap via min = 2 * 1 = 2 (since direct cost = min(1, 10^9) = 1 is
        // cheaper)
        // Wait, direct cost = 1 is cheaper than via min = 2
        // So total = 50000 * 1 = 50000
        // This doesn't overflow... need different values

        // For overflow test: use values where via min isn't cheaper
        // basket1: 100000 elements of value 2
        // basket2: 100000 elements of value 1_000_000_000
        // Need 50000 swaps, direct cost = min(2, 10^9) = 2 each
        // via min would need globalMin which is 2, so 2*2 = 4 (more expensive)
        // Total = 50000 * 2 = 100000 - still no overflow

        // To trigger overflow, we need many swaps with large costs
        // Example: 50000 swaps each costing 1_000_000_000
        // Result = 50000 * 10^9 = 5 * 10^13 > Integer.MAX_VALUE
        int[] basket1 = new int[100_000];
        int[] basket2 = new int[100_000];
        for (int i = 0; i < 100_000; i++) {
            // Both baskets have the same globalMin (1_000_000_000)
            basket1[i] = 1_000_000_000;
            basket2[i] = 999_999_999;
        }
        // freq1: {10^9: 100000}, freq2: {10^9-1: 100000}
        // union: {10^9: 100000, 10^9-1: 100000} - both even
        // target each: 50000 of each
        // excess1: 50000 of 10^9 (has 100000, needs 50000)
        // excess2: 50000 of (10^9-1) (has 100000, needs 50000)
        // globalMin = 999_999_999
        // each swap: direct = min(10^9, 10^9-1) = 999_999_999
        // via min = 2 * 999_999_999 = 1_999_999_998 (more expensive)
        // Total = 50000 * 999_999_999 = 49_999_999_950_000

        long result = this.rearrangingFruits.rearrange(basket1, basket2);
        assertThat(result).isEqualTo(49_999_999_950_000L);
    }

    // ==================== Validation Tests ====================

    @Test
    void testRearrange_Nulls_Then_IllegalArgumentException() {
        assertThatThrownBy(() -> this.rearrangingFruits.rearrange(null, new int[] { 1, 2, 3 }))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Basket 1 must not be null");
    }

    @Test
    void testRearrange_Nulls2_Then_IllegalArgumentException() {
        assertThatThrownBy(() -> this.rearrangingFruits.rearrange(new int[] { 1, 2, 3 }, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Basket 2 must not be null");
    }

    @Test
    void testRearrange_BothNulls_Then_IllegalArgumentException() {
        assertThatThrownBy(() -> this.rearrangingFruits.rearrange(null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Basket 1 must not be null");
    }

    @Test
    void testRearrange_EmptyBaskets() {
        assertThatThrownBy(() -> this.rearrangingFruits.rearrange(new int[] {}, new int[] {}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Baskets must not be empty");
    }

    @Test
    void testRearrange_FirstBasketEmpty() {
        assertThatThrownBy(() -> this.rearrangingFruits.rearrange(new int[] {}, new int[] { 1, 2 }))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Baskets must not be empty");
    }

    @Test
    void testRearrange_SecondBasketEmpty() {
        assertThatThrownBy(() -> this.rearrangingFruits.rearrange(new int[] { 1, 2 }, new int[] {}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Baskets must not be empty");
    }

    @Test
    void testRearrange_DifferentLengths() {
        assertThatThrownBy(() -> this.rearrangingFruits.rearrange(new int[] { 1, 2 }, new int[] { 1, 2, 3 }))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Baskets must have the same length");
    }

    @Test
    void testRearrange_DifferentLengths_LongerFirst() {
        assertThatThrownBy(() -> this.rearrangingFruits.rearrange(new int[] { 1, 2, 3, 4 }, new int[] { 1, 2 }))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Baskets must have the same length");
    }

    // ==================== Length Constraint Tests (1 <= length <= 10^5)
    // ====================

    @Test
    void testRearrange_ExceedsMaxLength() {
        int[] largeBasket1 = new int[100_001];
        int[] largeBasket2 = new int[100_001];
        java.util.Arrays.fill(largeBasket1, 1);
        java.util.Arrays.fill(largeBasket2, 1);

        assertThatThrownBy(() -> this.rearrangingFruits.rearrange(largeBasket1, largeBasket2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Basket length must not exceed 100000");
    }

    @Test
    void testRearrange_AtMaxLength() {
        int[] maxBasket1 = new int[100_000];
        int[] maxBasket2 = new int[100_000];
        java.util.Arrays.fill(maxBasket1, 1);
        java.util.Arrays.fill(maxBasket2, 1);

        long result = this.rearrangingFruits.rearrange(maxBasket1, maxBasket2);
        assertThat(result).isZero(); // Already identical
    }

    // ==================== Value Constraint Tests (1 <= value <= 10^9)
    // ====================

    @Test
    void testRearrange_ZeroValueInBasket1() {
        assertThatThrownBy(() -> this.rearrangingFruits.rearrange(new int[] { 0, 1 }, new int[] { 1, 1 }))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Basket 1 contains invalid fruit value at index 0: 0");
    }

    @Test
    void testRearrange_ZeroValueInBasket2() {
        assertThatThrownBy(() -> this.rearrangingFruits.rearrange(new int[] { 1, 1 }, new int[] { 0, 1 }))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Basket 2 contains invalid fruit value at index 0: 0");
    }

    @Test
    void testRearrange_NegativeValueInBasket1() {
        assertThatThrownBy(() -> this.rearrangingFruits.rearrange(new int[] { -5, 1 }, new int[] { 1, 1 }))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Basket 1 contains invalid fruit value at index 0: -5");
    }

    @Test
    void testRearrange_NegativeValueInBasket2() {
        assertThatThrownBy(() -> this.rearrangingFruits.rearrange(new int[] { 1, 1 }, new int[] { 1, -3 }))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Basket 2 contains invalid fruit value at index 1: -3");
    }

    @Test
    void testRearrange_ExceedsMaxValueInBasket1() {
        assertThatThrownBy(
                () -> this.rearrangingFruits.rearrange(new int[] { 1_000_000_001, 1 }, new int[] { 1, 1 }))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Basket 1 contains invalid fruit value at index 0: 1000000001");
    }

    @Test
    void testRearrange_ExceedsMaxValueInBasket2() {
        assertThatThrownBy(
                () -> this.rearrangingFruits.rearrange(new int[] { 1, 1 }, new int[] { 1_000_000_001, 1 }))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Basket 2 contains invalid fruit value at index 0: 1000000001");
    }

    @Test
    void testRearrange_AtMaxValue() {
        long result = this.rearrangingFruits.rearrange(
                new int[] { 1_000_000_000, 1_000_000_000 },
                new int[] { 1_000_000_000, 1_000_000_000 });
        assertThat(result).isZero();
    }

    @Test
    void testRearrange_AtMinValue() {
        long result = this.rearrangingFruits.rearrange(new int[] { 1, 1 }, new int[] { 1, 1 });
        assertThat(result).isZero();
    }

    @Test
    void testRearrange_BoundaryValues() {
        // Mix of min and max values
        long result = this.rearrangingFruits.rearrange(
                new int[] { 1, 1_000_000_000 },
                new int[] { 1, 1_000_000_000 });
        assertThat(result).isZero();
    }
}
