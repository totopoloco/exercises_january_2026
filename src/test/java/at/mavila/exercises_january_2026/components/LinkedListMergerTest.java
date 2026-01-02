package at.mavila.exercises_january_2026.components;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * TDD Test Cases for LinkedListMerger.
 *
 * Merge k sorted linked lists and return it as one sorted list.
 *
 * Constraints:
 * - k == lists.length
 * - 0 <= k <= 10^4
 * - 0 <= lists[i].length <= 500
 * - -10^4 <= lists[i][j] <= 10^4
 * - lists[i] is sorted in ascending order
 * - The sum of lists[i].length will not exceed 10^4
 */
@SpringBootTest
class LinkedListMergerTest {

    @Autowired
    private LinkedListMerger linkedListMerger;

    // ==================== Helper Method ====================

    /**
     * Helper method to convert a ListNode to an array of integers for easier
     * assertion.
     */
    private int[] toArray(ListNode head) {
        if (head == null) {
            return new int[0];
        }
        // First pass to count nodes
        int count = 0;
        ListNode current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        // Second pass to populate array
        int[] result = new int[count];
        current = head;
        for (int i = 0; i < count; i++) {
            result[i] = current.val;
            current = current.next;
        }
        return result;
    }

    /**
     * Helper method to create a ListNode from an array of integers.
     * Builds the list backwards since ListNode is immutable.
     */
    private ListNode fromArray(int[] values) {
        if (values == null || values.length == 0) {
            return null;
        }
        ListNode current = null;
        for (int i = values.length - 1; i >= 0; i--) {
            current = new ListNode(values[i], current);
        }
        return current;
    }

    // ==================== Example Test Cases ====================

    @Test
    void testMergeKLists_Example1_ThreeLists() {
        // Input: lists = [[1,4,5],[1,3,4],[2,6]]
        // Output: [1,1,2,3,4,4,5,6]
        ListNode l1 = new ListNode(1, new ListNode(4, new ListNode(5)));
        ListNode l2 = new ListNode(1, new ListNode(3, new ListNode(4)));
        ListNode l3 = new ListNode(2, new ListNode(6));

        ListNode[] lists = new ListNode[] { l1, l2, l3 };

        ListNode mergedList = this.linkedListMerger.mergeKLists(lists);
        assertThat(mergedList).isNotNull();
        assertThat(toArray(mergedList)).containsExactly(1, 1, 2, 3, 4, 4, 5, 6);
    }

    @Test
    void testMergeKLists_Example2_EmptyArray() {
        // Input: lists = []
        // Output: []
        ListNode[] lists = new ListNode[] {};

        ListNode mergedList = this.linkedListMerger.mergeKLists(lists);
        assertThat(mergedList).isNull();
    }

    @Test
    void testMergeKLists_Example3_ArrayWithEmptyList() {
        // Input: lists = [[]]
        // Output: []
        ListNode[] lists = new ListNode[] { null };

        ListNode mergedList = this.linkedListMerger.mergeKLists(lists);
        assertThat(mergedList).isNull();
    }

    // ==================== Empty and Null Cases ====================

    @Test
    void testMergeKLists_NullInput() {
        // Input: null
        // Output: null or handle gracefully
        assertThatThrownBy(() -> this.linkedListMerger.mergeKLists(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Input lists cannot be null");
    }

    @Test
    void testMergeKLists_AllEmptyLists() {
        // Input: lists = [[], [], []]
        // Output: []
        ListNode[] lists = new ListNode[] { null, null, null };

        ListNode mergedList = this.linkedListMerger.mergeKLists(lists);
        assertThat(mergedList).isNull();
    }

    @Test
    void testMergeKLists_SomeEmptyLists() {
        // Input: lists = [[], [1,2], []]
        // Output: [1,2]
        ListNode l2 = fromArray(new int[] { 1, 2 });
        ListNode[] lists = new ListNode[] { null, l2, null };

        ListNode mergedList = this.linkedListMerger.mergeKLists(lists);
        assertThat(mergedList).isNotNull();
        assertThat(toArray(mergedList)).containsExactly(1, 2);
    }

    // ==================== Single List Cases ====================

    @Test
    void testMergeKLists_SingleListWithOneElement() {
        // Input: lists = [[5]]
        // Output: [5]
        ListNode l1 = new ListNode(5);
        ListNode[] lists = new ListNode[] { l1 };

        ListNode mergedList = this.linkedListMerger.mergeKLists(lists);
        assertThat(mergedList).isNotNull();
        assertThat(toArray(mergedList)).containsExactly(5);
    }

    @Test
    void testMergeKLists_SingleListWithMultipleElements() {
        // Input: lists = [[1,2,3,4,5]]
        // Output: [1,2,3,4,5]
        ListNode l1 = fromArray(new int[] { 1, 2, 3, 4, 5 });
        ListNode[] lists = new ListNode[] { l1 };

        ListNode mergedList = this.linkedListMerger.mergeKLists(lists);
        assertThat(mergedList).isNotNull();
        assertThat(toArray(mergedList)).containsExactly(1, 2, 3, 4, 5);
    }

    // ==================== Two Lists Cases ====================

    @Test
    void testMergeKLists_TwoListsNoOverlap() {
        // Input: lists = [[1,2,3], [4,5,6]]
        // Output: [1,2,3,4,5,6]
        ListNode l1 = fromArray(new int[] { 1, 2, 3 });
        ListNode l2 = fromArray(new int[] { 4, 5, 6 });
        ListNode[] lists = new ListNode[] { l1, l2 };

        ListNode mergedList = this.linkedListMerger.mergeKLists(lists);
        assertThat(mergedList).isNotNull();
        assertThat(toArray(mergedList)).containsExactly(1, 2, 3, 4, 5, 6);
    }

    @Test
    void testMergeKLists_TwoListsInterleaved() {
        // Input: lists = [[1,3,5], [2,4,6]]
        // Output: [1,2,3,4,5,6]
        ListNode l1 = fromArray(new int[] { 1, 3, 5 });
        ListNode l2 = fromArray(new int[] { 2, 4, 6 });
        ListNode[] lists = new ListNode[] { l1, l2 };

        ListNode mergedList = this.linkedListMerger.mergeKLists(lists);
        assertThat(mergedList).isNotNull();
        assertThat(toArray(mergedList)).containsExactly(1, 2, 3, 4, 5, 6);
    }

    @Test
    void testMergeKLists_TwoListsSecondSmaller() {
        // Input: lists = [[10,20,30], [1,2,3]]
        // Output: [1,2,3,10,20,30]
        ListNode l1 = fromArray(new int[] { 10, 20, 30 });
        ListNode l2 = fromArray(new int[] { 1, 2, 3 });
        ListNode[] lists = new ListNode[] { l1, l2 };

        ListNode mergedList = this.linkedListMerger.mergeKLists(lists);
        assertThat(mergedList).isNotNull();
        assertThat(toArray(mergedList)).containsExactly(1, 2, 3, 10, 20, 30);
    }

    // ==================== Duplicate Values ====================

    @Test
    void testMergeKLists_AllSameValues() {
        // Input: lists = [[5,5,5], [5,5], [5]]
        // Output: [5,5,5,5,5,5]
        ListNode l1 = fromArray(new int[] { 5, 5, 5 });
        ListNode l2 = fromArray(new int[] { 5, 5 });
        ListNode l3 = fromArray(new int[] { 5 });
        ListNode[] lists = new ListNode[] { l1, l2, l3 };

        ListNode mergedList = this.linkedListMerger.mergeKLists(lists);
        assertThat(mergedList).isNotNull();
        assertThat(toArray(mergedList)).containsExactly(5, 5, 5, 5, 5, 5);
    }

    @Test
    void testMergeKLists_DuplicatesAcrossLists() {
        // Input: lists = [[1,2,3], [2,3,4], [3,4,5]]
        // Output: [1,2,2,3,3,3,4,4,5]
        ListNode l1 = fromArray(new int[] { 1, 2, 3 });
        ListNode l2 = fromArray(new int[] { 2, 3, 4 });
        ListNode l3 = fromArray(new int[] { 3, 4, 5 });
        ListNode[] lists = new ListNode[] { l1, l2, l3 };

        ListNode mergedList = this.linkedListMerger.mergeKLists(lists);
        assertThat(mergedList).isNotNull();
        assertThat(toArray(mergedList)).containsExactly(1, 2, 2, 3, 3, 3, 4, 4, 5);
    }

    // ==================== Negative Numbers ====================

    @Test
    void testMergeKLists_AllNegativeNumbers() {
        // Input: lists = [[-5,-3,-1], [-4,-2]]
        // Output: [-5,-4,-3,-2,-1]
        ListNode l1 = fromArray(new int[] { -5, -3, -1 });
        ListNode l2 = fromArray(new int[] { -4, -2 });
        ListNode[] lists = new ListNode[] { l1, l2 };

        ListNode mergedList = this.linkedListMerger.mergeKLists(lists);
        assertThat(mergedList).isNotNull();
        assertThat(toArray(mergedList)).containsExactly(-5, -4, -3, -2, -1);
    }

    @Test
    void testMergeKLists_MixedPositiveNegative() {
        // Input: lists = [[-3,-1,2], [-2,0,1]]
        // Output: [-3,-2,-1,0,1,2]
        ListNode l1 = fromArray(new int[] { -3, -1, 2 });
        ListNode l2 = fromArray(new int[] { -2, 0, 1 });
        ListNode[] lists = new ListNode[] { l1, l2 };

        ListNode mergedList = this.linkedListMerger.mergeKLists(lists);
        assertThat(mergedList).isNotNull();
        assertThat(toArray(mergedList)).containsExactly(-3, -2, -1, 0, 1, 2);
    }

    @Test
    void testMergeKLists_NegativeAndPositiveNoOverlap() {
        // Input: lists = [[-5,-4,-3], [3,4,5]]
        // Output: [-5,-4,-3,3,4,5]
        ListNode l1 = fromArray(new int[] { -5, -4, -3 });
        ListNode l2 = fromArray(new int[] { 3, 4, 5 });
        ListNode[] lists = new ListNode[] { l1, l2 };

        ListNode mergedList = this.linkedListMerger.mergeKLists(lists);
        assertThat(mergedList).isNotNull();
        assertThat(toArray(mergedList)).containsExactly(-5, -4, -3, 3, 4, 5);
    }

    // ==================== Boundary Values (Constraint: -10^4 to 10^4)
    // ====================

    @Test
    void testMergeKLists_MaximumValue() {
        // Using maximum constraint value: 10^4 = 10000
        ListNode l1 = fromArray(new int[] { 10000, 10000 });
        ListNode l2 = fromArray(new int[] { 10000 });
        ListNode[] lists = new ListNode[] { l1, l2 };

        ListNode mergedList = this.linkedListMerger.mergeKLists(lists);
        assertThat(mergedList).isNotNull();
        assertThat(toArray(mergedList)).containsExactly(10000, 10000, 10000);
    }

    @Test
    void testMergeKLists_MinimumValue() {
        // Using minimum constraint value: -10^4 = -10000
        ListNode l1 = fromArray(new int[] { -10000, -10000 });
        ListNode l2 = fromArray(new int[] { -10000 });
        ListNode[] lists = new ListNode[] { l1, l2 };

        ListNode mergedList = this.linkedListMerger.mergeKLists(lists);
        assertThat(mergedList).isNotNull();
        assertThat(toArray(mergedList)).containsExactly(-10000, -10000, -10000);
    }

    @Test
    void testMergeKLists_MinAndMaxValues() {
        // Input: lists = [[-10000], [10000]]
        // Output: [-10000, 10000]
        ListNode l1 = fromArray(new int[] { -10000 });
        ListNode l2 = fromArray(new int[] { 10000 });
        ListNode[] lists = new ListNode[] { l1, l2 };

        ListNode mergedList = this.linkedListMerger.mergeKLists(lists);
        assertThat(mergedList).isNotNull();
        assertThat(toArray(mergedList)).containsExactly(-10000, 10000);
    }

    @Test
    void testMergeKLists_ExtremeRange() {
        // Input: lists = [[-10000, 0], [0, 10000]]
        // Output: [-10000, 0, 0, 10000]
        ListNode l1 = fromArray(new int[] { -10000, 0 });
        ListNode l2 = fromArray(new int[] { 0, 10000 });
        ListNode[] lists = new ListNode[] { l1, l2 };

        ListNode mergedList = this.linkedListMerger.mergeKLists(lists);
        assertThat(mergedList).isNotNull();
        assertThat(toArray(mergedList)).containsExactly(-10000, 0, 0, 10000);
    }

    // ==================== Zero Values ====================

    @Test
    void testMergeKLists_AllZeros() {
        // Input: lists = [[0,0], [0], [0,0,0]]
        // Output: [0,0,0,0,0,0]
        ListNode l1 = fromArray(new int[] { 0, 0 });
        ListNode l2 = fromArray(new int[] { 0 });
        ListNode l3 = fromArray(new int[] { 0, 0, 0 });
        ListNode[] lists = new ListNode[] { l1, l2, l3 };

        ListNode mergedList = this.linkedListMerger.mergeKLists(lists);
        assertThat(mergedList).isNotNull();
        assertThat(toArray(mergedList)).containsExactly(0, 0, 0, 0, 0, 0);
    }

    @Test
    void testMergeKLists_ZerosWithOtherValues() {
        // Input: lists = [[-1,0,1], [0]]
        // Output: [-1,0,0,1]
        ListNode l1 = fromArray(new int[] { -1, 0, 1 });
        ListNode l2 = fromArray(new int[] { 0 });
        ListNode[] lists = new ListNode[] { l1, l2 };

        ListNode mergedList = this.linkedListMerger.mergeKLists(lists);
        assertThat(mergedList).isNotNull();
        assertThat(toArray(mergedList)).containsExactly(-1, 0, 0, 1);
    }

    // ==================== Different List Sizes ====================

    @Test
    void testMergeKLists_VeryDifferentSizes() {
        // One list much larger than others
        ListNode l1 = fromArray(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 });
        ListNode l2 = fromArray(new int[] { 5 });
        ListNode[] lists = new ListNode[] { l1, l2 };

        ListNode mergedList = this.linkedListMerger.mergeKLists(lists);
        assertThat(mergedList).isNotNull();
        assertThat(toArray(mergedList)).containsExactly(1, 2, 3, 4, 5, 5, 6, 7, 8, 9, 10);
    }

    @Test
    void testMergeKLists_SingleElementLists() {
        // Multiple single-element lists
        ListNode l1 = fromArray(new int[] { 3 });
        ListNode l2 = fromArray(new int[] { 1 });
        ListNode l3 = fromArray(new int[] { 4 });
        ListNode l4 = fromArray(new int[] { 2 });
        ListNode l5 = fromArray(new int[] { 5 });
        ListNode[] lists = new ListNode[] { l1, l2, l3, l4, l5 };

        ListNode mergedList = this.linkedListMerger.mergeKLists(lists);
        assertThat(mergedList).isNotNull();
        assertThat(toArray(mergedList)).containsExactly(1, 2, 3, 4, 5);
    }

    // ==================== Many Lists (k constraint: 0 <= k <= 10^4)
    // ====================

    @Test
    void testMergeKLists_ManySmallLists() {
        // 10 lists with 1 element each
        ListNode[] lists = new ListNode[10];
        for (int i = 0; i < 10; i++) {
            lists[i] = new ListNode(10 - i); // [10, 9, 8, ..., 1]
        }

        ListNode mergedList = this.linkedListMerger.mergeKLists(lists);
        assertThat(mergedList).isNotNull();
        assertThat(toArray(mergedList)).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    }

    @Test
    void testMergeKLists_ManyListsSomeEmpty() {
        // Mix of empty and non-empty lists
        ListNode[] lists = new ListNode[5];
        lists[0] = null;
        lists[1] = fromArray(new int[] { 1, 4 });
        lists[2] = null;
        lists[3] = fromArray(new int[] { 2, 3 });
        lists[4] = null;

        ListNode mergedList = this.linkedListMerger.mergeKLists(lists);
        assertThat(mergedList).isNotNull();
        assertThat(toArray(mergedList)).containsExactly(1, 2, 3, 4);
    }

    // ==================== Large Lists (lists[i].length constraint: 0 <= length <=
    // 500) ====================

    @Test
    void testMergeKLists_LargerList() {
        // List with 100 elements
        int[] values = new int[100];
        for (int i = 0; i < 100; i++) {
            values[i] = i;
        }
        ListNode l1 = fromArray(values);
        ListNode[] lists = new ListNode[] { l1 };

        ListNode mergedList = this.linkedListMerger.mergeKLists(lists);
        assertThat(mergedList).isNotNull();
        assertThat(toArray(mergedList)).hasSize(100);
        assertThat(toArray(mergedList)[0]).isEqualTo(0);
        assertThat(toArray(mergedList)[99]).isEqualTo(99);
    }

    @Test
    void testMergeKLists_TwoLargerLists() {
        // Two lists with 50 elements each, interleaved
        int[] values1 = new int[50];
        int[] values2 = new int[50];
        for (int i = 0; i < 50; i++) {
            values1[i] = i * 2; // 0, 2, 4, ..., 98
            values2[i] = i * 2 + 1; // 1, 3, 5, ..., 99
        }
        ListNode l1 = fromArray(values1);
        ListNode l2 = fromArray(values2);
        ListNode[] lists = new ListNode[] { l1, l2 };

        ListNode mergedList = this.linkedListMerger.mergeKLists(lists);
        assertThat(mergedList).isNotNull();
        int[] result = toArray(mergedList);
        assertThat(result).hasSize(100);
        // Verify it's sorted 0, 1, 2, ..., 99
        for (int i = 0; i < 100; i++) {
            assertThat(result[i]).isEqualTo(i);
        }
    }

    // ==================== Edge Cases with Sorted Order ====================

    @Test
    void testMergeKLists_AlreadySortedCombination() {
        // Lists that when concatenated are already sorted
        ListNode l1 = fromArray(new int[] { 1, 2 });
        ListNode l2 = fromArray(new int[] { 3, 4 });
        ListNode l3 = fromArray(new int[] { 5, 6 });
        ListNode[] lists = new ListNode[] { l1, l2, l3 };

        ListNode mergedList = this.linkedListMerger.mergeKLists(lists);
        assertThat(mergedList).isNotNull();
        assertThat(toArray(mergedList)).containsExactly(1, 2, 3, 4, 5, 6);
    }

    @Test
    void testMergeKLists_ReverseSortedOrder() {
        // Lists in reverse order (but each list is sorted ascending)
        ListNode l1 = fromArray(new int[] { 7, 8, 9 });
        ListNode l2 = fromArray(new int[] { 4, 5, 6 });
        ListNode l3 = fromArray(new int[] { 1, 2, 3 });
        ListNode[] lists = new ListNode[] { l1, l2, l3 };

        ListNode mergedList = this.linkedListMerger.mergeKLists(lists);
        assertThat(mergedList).isNotNull();
        assertThat(toArray(mergedList)).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9);
    }

    // ==================== Consecutive Values ====================

    @Test
    void testMergeKLists_ConsecutiveSequences() {
        // Input: lists = [[1,2,3], [4,5,6], [7,8,9]]
        // Output: [1,2,3,4,5,6,7,8,9]
        ListNode l1 = fromArray(new int[] { 1, 2, 3 });
        ListNode l2 = fromArray(new int[] { 4, 5, 6 });
        ListNode l3 = fromArray(new int[] { 7, 8, 9 });
        ListNode[] lists = new ListNode[] { l1, l2, l3 };

        ListNode mergedList = this.linkedListMerger.mergeKLists(lists);
        assertThat(mergedList).isNotNull();
        assertThat(toArray(mergedList)).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9);
    }

    // ==================== Stress Test Cases ====================

    @Test
    void testMergeKLists_MultipleListsWithSameElements() {
        // Three identical lists
        ListNode l1 = fromArray(new int[] { 1, 2, 3 });
        ListNode l2 = fromArray(new int[] { 1, 2, 3 });
        ListNode l3 = fromArray(new int[] { 1, 2, 3 });
        ListNode[] lists = new ListNode[] { l1, l2, l3 };

        ListNode mergedList = this.linkedListMerger.mergeKLists(lists);
        assertThat(mergedList).isNotNull();
        assertThat(toArray(mergedList)).containsExactly(1, 1, 1, 2, 2, 2, 3, 3, 3);
    }

    @Test
    void testMergeKLists_LargeNumberOfLists() {
        // 20 lists with varying sizes
        ListNode[] lists = new ListNode[20];
        int expectedTotal = 0;
        for (int i = 0; i < 20; i++) {
            int size = (i % 5) + 1; // sizes 1-5
            int[] values = new int[size];
            for (int j = 0; j < size; j++) {
                values[j] = i * 10 + j;
            }
            lists[i] = fromArray(values);
            expectedTotal += size;
        }

        ListNode mergedList = this.linkedListMerger.mergeKLists(lists);
        assertThat(mergedList).isNotNull();
        assertThat(toArray(mergedList)).hasSize(expectedTotal);
        // Verify the result is sorted
        int[] result = toArray(mergedList);
        for (int i = 0; i < result.length - 1; i++) {
            assertThat(result[i]).isLessThanOrEqualTo(result[i + 1]);
        }
    }

    // ==================== Constraint Validation: Sum of lengths <= 10^4
    // ====================

    @Test
    void testMergeKLists_ModerateTotalSize() {
        // Create multiple lists with total elements = 100
        ListNode[] lists = new ListNode[10];
        int[] baseValues = new int[10];
        for (int i = 0; i < 10; i++) {
            baseValues[i] = i;
        }
        for (int i = 0; i < 10; i++) {
            int[] values = new int[10];
            for (int j = 0; j < 10; j++) {
                values[j] = i * 10 + j;
            }
            lists[i] = fromArray(values);
        }

        ListNode mergedList = this.linkedListMerger.mergeKLists(lists);
        assertThat(mergedList).isNotNull();
        assertThat(toArray(mergedList)).hasSize(100);
        // Verify the result is sorted
        int[] result = toArray(mergedList);
        for (int i = 0; i < result.length - 1; i++) {
            assertThat(result[i]).isLessThanOrEqualTo(result[i + 1]);
        }
    }

    // ==================== First and Last Element Checks ====================

    @Test
    void testMergeKLists_VerifyFirstElement() {
        // Ensure the smallest element is first
        ListNode l1 = fromArray(new int[] { 5, 10, 15 });
        ListNode l2 = fromArray(new int[] { 2, 8, 12 });
        ListNode l3 = fromArray(new int[] { 1, 7, 20 });
        ListNode[] lists = new ListNode[] { l1, l2, l3 };

        ListNode mergedList = this.linkedListMerger.mergeKLists(lists);
        assertThat(mergedList).isNotNull();
        assertThat(mergedList.val).isEqualTo(1);
    }

    @Test
    void testMergeKLists_VerifyLastElement() {
        // Ensure the largest element is last
        ListNode l1 = fromArray(new int[] { 5, 10, 15 });
        ListNode l2 = fromArray(new int[] { 2, 8, 100 });
        ListNode l3 = fromArray(new int[] { 1, 7, 20 });
        ListNode[] lists = new ListNode[] { l1, l2, l3 };

        ListNode mergedList = this.linkedListMerger.mergeKLists(lists);
        assertThat(mergedList).isNotNull();
        int[] result = toArray(mergedList);
        assertThat(result[result.length - 1]).isEqualTo(100);
    }
}
