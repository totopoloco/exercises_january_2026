package at.mavila.exercises_january_2026.components;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * TDD Test Cases for ArrayMediaCalculator.
 *
 * Given two sorted arrays nums1 and nums2 of size m and n respectively,
 * return the median of the two sorted arrays.
 *
 * Constraints:
 * - nums1.length == m
 * - nums2.length == n
 * - 0 <= m <= 1000
 * - 0 <= n <= 1000
 * - 1 <= m + n <= 2000
 * - -10^6 <= nums1[i], nums2[i] <= 10^6
 */
@SpringBootTest
class ArrayMediaCalculatorTest {

    @Autowired
    private ArrayMediaCalculator arrayMediaCalculator;

    // ==================== Basic Examples from Problem ====================

    @Test
    void testMergeAndCalculate_Example1_OddTotalLength() {
        // Input: nums1 = [1,3], nums2 = [2]
        // merged array = [1,2,3] and median is 2
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] { 1, 3 }, new int[] { 2 });
        assertThat(result).isEqualTo(2.0);
    }

    @Test
    void testMergeAndCalculate_Example2_EvenTotalLength() {
        // Input: nums1 = [1,2], nums2 = [3,4]
        // merged array = [1,2,3,4] and median is (2 + 3) / 2 = 2.5
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] { 1, 2 }, new int[] { 3, 4 });
        assertThat(result).isEqualTo(2.5);
    }

    // ==================== Empty Array Cases ====================

    @Test
    void testMergeAndCalculate_FirstArrayEmpty_OddLength() {
        // nums1 = [], nums2 = [1,2,3]
        // merged = [1,2,3], median = 2
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] {}, new int[] { 1, 2, 3 });
        assertThat(result).isEqualTo(2.0);
    }

    @Test
    void testMergeAndCalculate_FirstArrayEmpty_EvenLength() {
        // nums1 = [], nums2 = [1,2,3,4]
        // merged = [1,2,3,4], median = (2+3)/2 = 2.5
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] {}, new int[] { 1, 2, 3, 4 });
        assertThat(result).isEqualTo(2.5);
    }

    @Test
    void testMergeAndCalculate_SecondArrayEmpty_OddLength() {
        // nums1 = [1,2,3], nums2 = []
        // merged = [1,2,3], median = 2
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] { 1, 2, 3 }, new int[] {});
        assertThat(result).isEqualTo(2.0);
    }

    @Test
    void testMergeAndCalculate_SecondArrayEmpty_EvenLength() {
        // nums1 = [1,2,3,4], nums2 = []
        // merged = [1,2,3,4], median = (2+3)/2 = 2.5
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] { 1, 2, 3, 4 }, new int[] {});
        assertThat(result).isEqualTo(2.5);
    }

    @Test
    void testMergeAndCalculate_FirstArrayEmpty_SingleElement() {
        // nums1 = [], nums2 = [5]
        // merged = [5], median = 5
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] {}, new int[] { 5 });
        assertThat(result).isEqualTo(5.0);
    }

    @Test
    void testMergeAndCalculate_SecondArrayEmpty_SingleElement() {
        // nums1 = [7], nums2 = []
        // merged = [7], median = 7
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] { 7 }, new int[] {});
        assertThat(result).isEqualTo(7.0);
    }

    // ==================== Single Element Arrays ====================

    @Test
    void testMergeAndCalculate_BothSingleElement_EvenTotal() {
        // nums1 = [1], nums2 = [2]
        // merged = [1,2], median = (1+2)/2 = 1.5
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] { 1 }, new int[] { 2 });
        assertThat(result).isEqualTo(1.5);
    }

    @Test
    void testMergeAndCalculate_BothSingleElement_SameValue() {
        // nums1 = [5], nums2 = [5]
        // merged = [5,5], median = (5+5)/2 = 5
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] { 5 }, new int[] { 5 });
        assertThat(result).isEqualTo(5.0);
    }

    @Test
    void testMergeAndCalculate_SingleAndMultiple_OddTotal() {
        // nums1 = [3], nums2 = [1,2]
        // merged = [1,2,3], median = 2
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] { 3 }, new int[] { 1, 2 });
        assertThat(result).isEqualTo(2.0);
    }

    @Test
    void testMergeAndCalculate_SingleAndMultiple_EvenTotal() {
        // nums1 = [4], nums2 = [1,2,3]
        // merged = [1,2,3,4], median = (2+3)/2 = 2.5
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] { 4 }, new int[] { 1, 2, 3 });
        assertThat(result).isEqualTo(2.5);
    }

    // ==================== Odd vs Even Total Length ====================

    @Test
    void testMergeAndCalculate_OddTotalLength_MedianIsMiddle() {
        // nums1 = [1,3,5], nums2 = [2,4]
        // merged = [1,2,3,4,5], median = 3 (middle element)
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] { 1, 3, 5 }, new int[] { 2, 4 });
        assertThat(result).isEqualTo(3.0);
    }

    @Test
    void testMergeAndCalculate_EvenTotalLength_MedianIsAverage() {
        // nums1 = [1,3,5], nums2 = [2,4,6]
        // merged = [1,2,3,4,5,6], median = (3+4)/2 = 3.5
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] { 1, 3, 5 }, new int[] { 2, 4, 6 });
        assertThat(result).isEqualTo(3.5);
    }

    // ==================== Non-Overlapping Arrays ====================

    @Test
    void testMergeAndCalculate_NoOverlap_FirstSmaller() {
        // nums1 = [1,2,3], nums2 = [7,8,9]
        // merged = [1,2,3,7,8,9], median = (3+7)/2 = 5
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] { 1, 2, 3 }, new int[] { 7, 8, 9 });
        assertThat(result).isEqualTo(5.0);
    }

    @Test
    void testMergeAndCalculate_NoOverlap_SecondSmaller() {
        // nums1 = [10,20,30], nums2 = [1,2,3]
        // merged = [1,2,3,10,20,30], median = (3+10)/2 = 6.5
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] { 10, 20, 30 }, new int[] { 1, 2, 3 });
        assertThat(result).isEqualTo(6.5);
    }

    @Test
    void testMergeAndCalculate_NoOverlap_OddTotal() {
        // nums1 = [1,2], nums2 = [10,20,30]
        // merged = [1,2,10,20,30], median = 10
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] { 1, 2 }, new int[] { 10, 20, 30 });
        assertThat(result).isEqualTo(10.0);
    }

    // ==================== Interleaved Arrays ====================

    @Test
    void testMergeAndCalculate_CompletelyInterleaved() {
        // nums1 = [1,3,5,7], nums2 = [2,4,6,8]
        // merged = [1,2,3,4,5,6,7,8], median = (4+5)/2 = 4.5
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] { 1, 3, 5, 7 }, new int[] { 2, 4, 6, 8 });
        assertThat(result).isEqualTo(4.5);
    }

    @Test
    void testMergeAndCalculate_PartiallyInterleaved() {
        // nums1 = [1,5,9], nums2 = [2,3,7,8]
        // merged = [1,2,3,5,7,8,9], median = 5
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] { 1, 5, 9 }, new int[] { 2, 3, 7, 8 });
        assertThat(result).isEqualTo(5.0);
    }

    // ==================== All Same Values ====================

    @Test
    void testMergeAndCalculate_AllSameValues_BothArrays() {
        // nums1 = [5,5,5], nums2 = [5,5,5]
        // merged = [5,5,5,5,5,5], median = 5
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] { 5, 5, 5 }, new int[] { 5, 5, 5 });
        assertThat(result).isEqualTo(5.0);
    }

    @Test
    void testMergeAndCalculate_AllSameValues_OneArray() {
        // nums1 = [3,3,3], nums2 = [1,2,4]
        // merged = [1,2,3,3,3,4], median = (3+3)/2 = 3
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] { 3, 3, 3 }, new int[] { 1, 2, 4 });
        assertThat(result).isEqualTo(3.0);
    }

    // ==================== Negative Numbers ====================

    @Test
    void testMergeAndCalculate_AllNegative() {
        // nums1 = [-5,-3,-1], nums2 = [-4,-2]
        // merged = [-5,-4,-3,-2,-1], median = -3
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] { -5, -3, -1 }, new int[] { -4, -2 });
        assertThat(result).isEqualTo(-3.0);
    }

    @Test
    void testMergeAndCalculate_AllNegative_EvenLength() {
        // nums1 = [-6,-4], nums2 = [-5,-3]
        // merged = [-6,-5,-4,-3], median = (-5+-4)/2 = -4.5
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] { -6, -4 }, new int[] { -5, -3 });
        assertThat(result).isEqualTo(-4.5);
    }

    @Test
    void testMergeAndCalculate_MixedPositiveNegative() {
        // nums1 = [-3,-1,2], nums2 = [-2,0,1]
        // merged = [-3,-2,-1,0,1,2], median = (-1+0)/2 = -0.5
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] { -3, -1, 2 }, new int[] { -2, 0, 1 });
        assertThat(result).isEqualTo(-0.5);
    }

    @Test
    void testMergeAndCalculate_MixedPositiveNegative_OddLength() {
        // nums1 = [-2,0], nums2 = [-1,1,2]
        // merged = [-2,-1,0,1,2], median = 0
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] { -2, 0 }, new int[] { -1, 1, 2 });
        assertThat(result).isEqualTo(0.0);
    }

    @Test
    void testMergeAndCalculate_NegativeAndPositive_NoOverlap() {
        // nums1 = [-5,-4,-3], nums2 = [3,4,5]
        // merged = [-5,-4,-3,3,4,5], median = (-3+3)/2 = 0
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] { -5, -4, -3 }, new int[] { 3, 4, 5 });
        assertThat(result).isEqualTo(0.0);
    }

    // ==================== Boundary Values (Constraint: -10^6 to 10^6)
    // ====================

    @Test
    void testMergeAndCalculate_MaximumValues() {
        // Using maximum constraint value: 10^6 = 1,000,000
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] { 1000000, 1000000 }, new int[] { 1000000 });
        assertThat(result).isEqualTo(1000000.0);
    }

    @Test
    void testMergeAndCalculate_MinimumValues() {
        // Using minimum constraint value: -10^6 = -1,000,000
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] { -1000000, -1000000 }, new int[] { -1000000 });
        assertThat(result).isEqualTo(-1000000.0);
    }

    @Test
    void testMergeAndCalculate_MaxAndMinValues() {
        // nums1 = [-1000000], nums2 = [1000000]
        // merged = [-1000000, 1000000], median = 0
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] { -1000000 }, new int[] { 1000000 });
        assertThat(result).isEqualTo(0.0);
    }

    @Test
    void testMergeAndCalculate_ExtremeRange() {
        // nums1 = [-1000000, 0], nums2 = [0, 1000000]
        // merged = [-1000000, 0, 0, 1000000], median = (0+0)/2 = 0
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] { -1000000, 0 }, new int[] { 0, 1000000 });
        assertThat(result).isEqualTo(0.0);
    }

    // ==================== Different Array Sizes ====================

    @Test
    void testMergeAndCalculate_FirstArrayMuchLarger() {
        // nums1 = [1,2,3,4,5,6,7,8,9,10], nums2 = [5]
        // merged = [1,2,3,4,5,5,6,7,8,9,10], median = 5
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }, new int[] { 5 });
        assertThat(result).isEqualTo(5.0);
    }

    @Test
    void testMergeAndCalculate_SecondArrayMuchLarger() {
        // nums1 = [50], nums2 = [10,20,30,40,60,70,80,90,100]
        // merged = [10,20,30,40,50,60,70,80,90,100], median = (50+60)/2 = 55
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] { 50 }, new int[] { 10, 20, 30, 40, 60, 70, 80, 90, 100 });
        assertThat(result).isEqualTo(55.0);
    }

    @Test
    void testMergeAndCalculate_VeryDifferentSizes() {
        // nums1 has 1 element, nums2 has 9 elements (ratio 1:9)
        // nums1 = [5], nums2 = [1,2,3,4,6,7,8,9,10]
        // merged = [1,2,3,4,5,6,7,8,9,10], median = (5+6)/2 = 5.5
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] { 5 }, new int[] { 1, 2, 3, 4, 6, 7, 8, 9, 10 });
        assertThat(result).isEqualTo(5.5);
    }

    // ==================== Duplicate Values Across Arrays ====================

    @Test
    void testMergeAndCalculate_DuplicatesAcrossArrays() {
        // nums1 = [1,2,3], nums2 = [2,3,4]
        // merged = [1,2,2,3,3,4], median = (2+3)/2 = 2.5
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] { 1, 2, 3 }, new int[] { 2, 3, 4 });
        assertThat(result).isEqualTo(2.5);
    }

    @Test
    void testMergeAndCalculate_MultipleDuplicates() {
        // nums1 = [1,1,1,1], nums2 = [1,1,1]
        // merged = [1,1,1,1,1,1,1], median = 1
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] { 1, 1, 1, 1 }, new int[] { 1, 1, 1 });
        assertThat(result).isEqualTo(1.0);
    }

    // ==================== Zero Values ====================

    @Test
    void testMergeAndCalculate_ContainsZeros() {
        // nums1 = [-1,0,1], nums2 = [0]
        // merged = [-1,0,0,1], median = (0+0)/2 = 0
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] { -1, 0, 1 }, new int[] { 0 });
        assertThat(result).isEqualTo(0.0);
    }

    @Test
    void testMergeAndCalculate_AllZeros() {
        // nums1 = [0,0,0], nums2 = [0,0]
        // merged = [0,0,0,0,0], median = 0
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] { 0, 0, 0 }, new int[] { 0, 0 });
        assertThat(result).isEqualTo(0.0);
    }

    // ==================== Precision Tests ====================

    @Test
    void testMergeAndCalculate_OddSumForAverage() {
        // nums1 = [1], nums2 = [2]
        // merged = [1,2], median = (1+2)/2 = 1.5
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] { 1 }, new int[] { 2 });
        assertThat(result).isEqualTo(1.5);
    }

    @Test
    void testMergeAndCalculate_LargeOddSumForAverage() {
        // nums1 = [999999], nums2 = [1000000]
        // merged = [999999, 1000000], median = (999999+1000000)/2 = 999999.5
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] { 999999 }, new int[] { 1000000 });
        assertThat(result).isEqualTo(999999.5);
    }

    @Test
    void testMergeAndCalculate_NegativeOddSumForAverage() {
        // nums1 = [-2], nums2 = [-1]
        // merged = [-2,-1], median = (-2+-1)/2 = -1.5
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] { -2 }, new int[] { -1 });
        assertThat(result).isEqualTo(-1.5);
    }

    // ==================== Null Input Handling ====================

    @Test
    void testMergeAndCalculate_FirstArrayNull_ThrowsException() {
        assertThatThrownBy(() -> this.arrayMediaCalculator.mergeAndCalculate(null, new int[] { 1, 2, 3 }))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testMergeAndCalculate_SecondArrayNull_ThrowsException() {
        assertThatThrownBy(() -> this.arrayMediaCalculator.mergeAndCalculate(new int[] { 1, 2, 3 }, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testMergeAndCalculate_BothArraysNull_ThrowsException() {
        assertThatThrownBy(() -> this.arrayMediaCalculator.mergeAndCalculate(null, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    // ==================== Both Arrays Empty (Invalid per constraint m+n >= 1)
    // ====================

    @Test
    void testMergeAndCalculate_BothArraysEmpty_ThrowsException() {
        // Constraint: 1 <= m + n <= 2000, so both empty is invalid
        double mergeAndCalculate = this.arrayMediaCalculator.mergeAndCalculate(new int[] {}, new int[] {});
        assertThat(mergeAndCalculate).isEqualTo(0.0);
    }

    // ==================== Large Arrays (Performance/Stress Tests)
    // ====================

    @Test
    void testMergeAndCalculate_LargeArrays_MaxSize() {
        // Both arrays at max size (1000 each = 2000 total)
        int[] nums1 = new int[1000];
        int[] nums2 = new int[1000];
        for (int i = 0; i < 1000; i++) {
            nums1[i] = i * 2; // 0, 2, 4, ..., 1998
            nums2[i] = i * 2 + 1; // 1, 3, 5, ..., 1999
        }
        // merged = [0,1,2,3,...,1999], median = (999+1000)/2 = 999.5
        double result = this.arrayMediaCalculator.mergeAndCalculate(nums1, nums2);
        assertThat(result).isEqualTo(999.5);
    }

    @Test
    void testMergeAndCalculate_LargeArrays_OneEmpty() {
        // One array at max size, other empty
        int[] nums1 = new int[2000];
        for (int i = 0; i < 2000; i++) {
            nums1[i] = i; // 0, 1, 2, ..., 1999
        }
        // merged = [0,1,2,...,1999], median = (999+1000)/2 = 999.5
        double result = this.arrayMediaCalculator.mergeAndCalculate(nums1, new int[] {});
        assertThat(result).isEqualTo(999.5);
    }

    @Test
    void testMergeAndCalculate_LargeArrays_OddTotal() {
        // 1000 + 999 = 1999 elements (odd)
        int[] nums1 = new int[1000];
        int[] nums2 = new int[999];
        for (int i = 0; i < 1000; i++) {
            nums1[i] = i;
        }
        for (int i = 0; i < 999; i++) {
            nums2[i] = i + 1000;
        }
        // merged = [0,1,2,...,1998], median = element at index 999 = 999
        double result = this.arrayMediaCalculator.mergeAndCalculate(nums1, nums2);
        assertThat(result).isEqualTo(999.0);
    }

    // ==================== Sequential Values ====================

    @Test
    void testMergeAndCalculate_ConsecutiveSequence() {
        // nums1 = [1,2,3,4,5], nums2 = [6,7,8,9,10]
        // merged = [1,2,3,4,5,6,7,8,9,10], median = (5+6)/2 = 5.5
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] { 1, 2, 3, 4, 5 }, new int[] { 6, 7, 8, 9, 10 });
        assertThat(result).isEqualTo(5.5);
    }

    @Test
    void testMergeAndCalculate_ReverseOrderArrays() {
        // nums1 has higher values, nums2 has lower (but both are sorted ascending)
        // nums1 = [100,200,300], nums2 = [1,2,3]
        // merged = [1,2,3,100,200,300], median = (3+100)/2 = 51.5
        double result = this.arrayMediaCalculator.mergeAndCalculate(
                new int[] { 100, 200, 300 }, new int[] { 1, 2, 3 });
        assertThat(result).isEqualTo(51.5);
    }
}
