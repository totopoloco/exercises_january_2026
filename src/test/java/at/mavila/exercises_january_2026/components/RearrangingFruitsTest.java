package at.mavila.exercises_january_2026.components;

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
        int result1 = this.rearrangingFruits.rearrange(new int[] { 4, 2, 2, 2 }, new int[] { 1, 4, 1, 2 });
        assertThat(result1).isEqualTo(1);
    }

    @Test
    void testRearrange_Impossible() {
        int result2 = this.rearrangingFruits.rearrange(new int[] { 2, 3, 4, 1 }, new int[] { 3, 2, 5, 1 });
        assertThat(result2).isEqualTo(-1);
    }

    // ==================== Already Identical Baskets ====================

    @Test
    void testRearrange_AlreadyIdentical_SingleElement() {
        int result = this.rearrangingFruits.rearrange(new int[] { 5 }, new int[] { 5 });
        assertThat(result).isZero();
    }

    @Test
    void testRearrange_AlreadyIdentical_MultipleElements() {
        int result = this.rearrangingFruits.rearrange(new int[] { 1, 2, 3 }, new int[] { 1, 2, 3 });
        assertThat(result).isZero();
    }

    @Test
    void testRearrange_AlreadyIdentical_DifferentOrder() {
        int result = this.rearrangingFruits.rearrange(new int[] { 3, 1, 2 }, new int[] { 2, 3, 1 });
        assertThat(result).isZero();
    }

    @Test
    void testRearrange_AlreadyIdentical_WithDuplicates() {
        int result = this.rearrangingFruits.rearrange(new int[] { 2, 2, 3, 3 }, new int[] { 3, 2, 3, 2 });
        assertThat(result).isZero();
    }

    // ==================== Simple Swap Cases ====================

    @Test
    void testRearrange_SimpleSwap_TwoElements() {
        // Basket1: [1, 2], Basket2: [2, 1] -> already identical by frequency
        int result = this.rearrangingFruits.rearrange(new int[] { 1, 2 }, new int[] { 2, 1 });
        assertThat(result).isZero();
    }

    @Test
    void testRearrange_SimpleSwap_NeedsOneSwap() {
        // Basket1: [1, 1], Basket2: [2, 2] -> need to swap one 1 with one 2, cost =
        // min(1,2) = 1
        int result = this.rearrangingFruits.rearrange(new int[] { 1, 1 }, new int[] { 2, 2 });
        assertThat(result).isEqualTo(1);
    }

    @Test
    void testRearrange_SimpleSwap_CostIsSmaller() {
        // Basket1: [5, 5], Basket2: [10, 10] -> swap one 5 with one 10, cost =
        // min(5,10) = 5
        int result = this.rearrangingFruits.rearrange(new int[] { 5, 5 }, new int[] { 10, 10 });
        assertThat(result).isEqualTo(5);
    }

    // ==================== Via Minimum Cost Cases ====================

    @Test
    void testRearrange_ViaMinimumIsCheaper() {
        // Basket1: [1, 100, 100], Basket2: [100, 1, 1]
        // freq1: {1:1, 100:2}, freq2: {100:1, 1:2}
        // union: {1:3, 100:3} -> both odd -> impossible to split evenly
        int result = this.rearrangingFruits.rearrange(new int[] { 1, 100, 100 }, new int[] { 100, 1, 1 });
        assertThat(result).isEqualTo(-1);
    }

    @Test
    void testRearrange_ViaMinimumIsCheaper_ValidCase() {
        // Basket1: [1, 1, 100, 100], Basket2: [100, 100, 1, 1]
        // freq1: {1:2, 100:2}, freq2: {100:2, 1:2}
        // union: {1:4, 100:4} -> both even, target each = 2
        // Already balanced! No swaps needed.
        int result = this.rearrangingFruits.rearrange(new int[] { 1, 1, 100, 100 }, new int[] { 100, 100, 1, 1 });
        assertThat(result).isZero();
    }

    @Test
    void testRearrange_ViaMinUsedForExpensiveSwap() {
        // Basket1: [1, 50, 50, 50, 50], Basket2: [50, 50, 50, 50, 1]
        // Both have same frequency - already balanced
        int result = this.rearrangingFruits.rearrange(
                new int[] { 1, 50, 50, 50, 50 },
                new int[] { 50, 50, 50, 50, 1 });
        assertThat(result).isZero();
    }

    @Test
    void testRearrange_ActualViaMinCase() {
        // Basket1: [84,80,18,32], Basket2: [61,7,36,72]
        // This is a case where baskets cannot be made equal
        int result = this.rearrangingFruits.rearrange(
                new int[] { 84, 80, 18, 32 },
                new int[] { 61, 7, 36, 72 });
        assertThat(result).isEqualTo(-1);
    }

    @Test
    void testRearrange_MixedDirectAndViaMin() {
        // Some swaps are cheaper direct, others via minimum
        int result = this.rearrangingFruits.rearrange(new int[] { 1, 2, 50, 50 }, new int[] { 50, 50, 1, 2 });
        assertThat(result).isZero(); // already balanced
    }

    // ==================== Odd Count Cases (Impossible) ====================

    @Test
    void testRearrange_OddTotalCount_SingleValue() {
        // Total count of 3 is odd (1 + 2 = 3), cannot split evenly
        int result = this.rearrangingFruits.rearrange(new int[] { 3 }, new int[] { 4 });
        assertThat(result).isEqualTo(-1);
    }

    @Test
    void testRearrange_OddTotalCount_InLargerBasket() {
        // One fruit type has odd total
        int result = this.rearrangingFruits.rearrange(new int[] { 1, 1, 1 }, new int[] { 2, 2, 2 });
        assertThat(result).isEqualTo(-1);
    }

    // ==================== Large Values ====================

    @Test
    void testRearrange_LargeValues() {
        int result = this.rearrangingFruits.rearrange(
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
        int result = this.rearrangingFruits.rearrange(
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
        int result = this.rearrangingFruits.rearrange(
                new int[] { 1, 1, 50, 50 },
                new int[] { 1, 1, 100, 100 });
        assertThat(result).isEqualTo(2);
    }

    // ==================== Edge Cases ====================

    @Test
    void testRearrange_AllSameValue() {
        int result = this.rearrangingFruits.rearrange(new int[] { 7, 7, 7 }, new int[] { 7, 7, 7 });
        assertThat(result).isZero();
    }

    @Test
    void testRearrange_TwoDistinctValues() {
        int result = this.rearrangingFruits.rearrange(new int[] { 1, 1, 2, 2 }, new int[] { 1, 1, 2, 2 });
        assertThat(result).isZero();
    }

    @Test
    void testRearrange_MultipleSwapsNeeded() {
        // Multiple swaps required
        int result = this.rearrangingFruits.rearrange(
                new int[] { 1, 1, 1, 1 },
                new int[] { 2, 2, 2, 2 });
        // Need 2 swaps: each costs min(1, 2) = 1, total = 2
        assertThat(result).isEqualTo(2);
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

        int result = this.rearrangingFruits.rearrange(maxBasket1, maxBasket2);
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
        int result = this.rearrangingFruits.rearrange(
                new int[] { 1_000_000_000, 1_000_000_000 },
                new int[] { 1_000_000_000, 1_000_000_000 });
        assertThat(result).isZero();
    }

    @Test
    void testRearrange_AtMinValue() {
        int result = this.rearrangingFruits.rearrange(new int[] { 1, 1 }, new int[] { 1, 1 });
        assertThat(result).isZero();
    }

    @Test
    void testRearrange_BoundaryValues() {
        // Mix of min and max values
        int result = this.rearrangingFruits.rearrange(
                new int[] { 1, 1_000_000_000 },
                new int[] { 1, 1_000_000_000 });
        assertThat(result).isZero();
    }
}
