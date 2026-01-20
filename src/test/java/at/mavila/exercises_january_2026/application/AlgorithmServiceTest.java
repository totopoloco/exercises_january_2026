package at.mavila.exercises_january_2026.application;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import at.mavila.exercises_january_2026.domain.collection.ListNode;

@SpringBootTest
@DisplayName("AlgorithmService Tests")
class AlgorithmServiceTest {

  @Autowired
  private AlgorithmService algorithmService;

  @Nested
  @DisplayName("Array Median Calculator")
  class ArrayMedianCalculatorTests {

    @Test
    @DisplayName("Should calculate median of two arrays with odd combined length")
    void shouldCalculateMedianOddLength() {
      int[] array1 = { 1, 3 };
      int[] array2 = { 2 };
      double result = algorithmService.calculateMedian(array1, array2);
      assertEquals(2.0, result);
    }

    @Test
    @DisplayName("Should calculate median of two arrays with even combined length")
    void shouldCalculateMedianEvenLength() {
      int[] array1 = { 1, 2 };
      int[] array2 = { 3, 4 };
      double result = algorithmService.calculateMedian(array1, array2);
      assertEquals(2.5, result);
    }
  }

  @Nested
  @DisplayName("Container Water")
  class ContainerWaterTests {

    @Test
    @DisplayName("Should find maximum water area")
    void shouldFindMaxWaterArea() {
      int[] heights = { 1, 8, 6, 2, 5, 4, 8, 3, 7 };
      int result = algorithmService.calculateMaxWaterArea(heights);
      assertEquals(49, result);
    }
  }

  @Nested
  @DisplayName("Count Negatives")
  class CountNegativesTests {

    @Test
    @DisplayName("Should count negative numbers in grid")
    void shouldCountNegatives() {
      int[][] grid = {
          { 4, 3, 2, -1 },
          { 3, 2, 1, -1 },
          { 1, 1, -1, -2 },
          { -1, -1, -2, -3 }
      };
      int result = algorithmService.countNegatives(grid);
      assertEquals(8, result);
    }
  }

  @Nested
  @DisplayName("Letter Combinations")
  class LetterCombinationsTests {

    @Test
    @DisplayName("Should generate letter combinations for digits")
    void shouldGenerateLetterCombinations() {
      List<String> result = algorithmService.getLetterCombinations("23");
      assertEquals(9, result.size());
      assertTrue(result.contains("ad"));
      assertTrue(result.contains("be"));
      assertTrue(result.contains("cf"));
    }
  }

  @Nested
  @DisplayName("Linked List Merger")
  class LinkedListMergerTests {

    @Test
    @DisplayName("Should merge k sorted linked lists")
    void shouldMergeKSortedLists() {
      ListNode list1 = new ListNode(1, new ListNode(4, new ListNode(5)));
      ListNode list2 = new ListNode(1, new ListNode(3, new ListNode(4)));
      ListNode list3 = new ListNode(2, new ListNode(6));

      ListNode[] lists = { list1, list2, list3 };
      ListNode result = algorithmService.mergeKSortedLists(lists);

      // Verify the merged list is sorted
      assertNotNull(result);
      int prev = Integer.MIN_VALUE;
      while (result != null) {
        assertTrue(result.val >= prev);
        prev = result.val;
        result = result.next;
      }
    }
  }

  @Nested
  @DisplayName("Longest Palindrome Substring")
  class LongestPalindromeTests {

    @Test
    @DisplayName("Should find longest palindrome substring")
    void shouldFindLongestPalindrome() {
      String result = algorithmService.findLongestPalindrome("babad");
      assertTrue(result.equals("bab") || result.equals("aba"));
    }

    @Test
    @DisplayName("Should handle even length palindrome")
    void shouldHandleEvenLengthPalindrome() {
      String result = algorithmService.findLongestPalindrome("cbbd");
      assertEquals("bb", result);
    }
  }

  @Nested
  @DisplayName("Longest Unique Substring")
  class LongestUniqueSubstringTests {

    @Test
    @DisplayName("Should find length of longest unique substring")
    void shouldFindLongestUniqueSubstringLength() {
      int result = algorithmService.findLongestUniqueSubstringLength("abcabcbb");
      assertEquals(3, result);
    }

    @Test
    @DisplayName("Should handle all same characters")
    void shouldHandleAllSameCharacters() {
      int result = algorithmService.findLongestUniqueSubstringLength("bbbbb");
      assertEquals(1, result);
    }
  }

  @Nested
  @DisplayName("Rearranging Fruits")
  class RearrangingFruitsTests {

    @Test
    @DisplayName("Should calculate minimum rearrangement cost")
    void shouldCalculateMinRearrangementCost() {
      int[] basket1 = { 4, 2, 2, 2 };
      int[] basket2 = { 1, 4, 1, 2 };
      long result = algorithmService.rearrangeFruits(basket1, basket2);
      assertEquals(1, result);
    }

    @Test
    @DisplayName("Should return -1 when rearrangement is impossible")
    void shouldReturnMinusOneWhenImpossible() {
      int[] basket1 = { 1, 2, 3 };
      int[] basket2 = { 4, 5, 6 };
      long result = algorithmService.rearrangeFruits(basket1, basket2);
      assertEquals(-1, result);
    }
  }
}
