package at.mavila.exercises_january_2026.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import at.mavila.exercises_january_2026.components.ArrayMediaCalculator;
import at.mavila.exercises_january_2026.components.ContainerWater;
import at.mavila.exercises_january_2026.components.CountNegativesSortedMix;
import at.mavila.exercises_january_2026.components.LetterCombinations;
import at.mavila.exercises_january_2026.components.LinkedListMerger;
import at.mavila.exercises_january_2026.components.ListNode;
import at.mavila.exercises_january_2026.components.LongestPalindromeSubstringExtractor;
import at.mavila.exercises_january_2026.components.LongestSubstring;
import at.mavila.exercises_january_2026.components.PinCracker;
import at.mavila.exercises_january_2026.components.RearrangingFruits;
import at.mavila.exercises_january_2026.components.RomanNumberProcessor;
import lombok.RequiredArgsConstructor;

/**
 * Service layer that aggregates all algorithm components.
 * This service acts as a facade for the GraphQL layer to access all algorithms.
 */
@Service
@RequiredArgsConstructor
public class AlgorithmService {

  private final ArrayMediaCalculator arrayMediaCalculator;
  private final ContainerWater containerWater;
  private final CountNegativesSortedMix countNegativesSortedMix;
  private final LetterCombinations letterCombinations;
  private final LinkedListMerger linkedListMerger;
  private final LongestPalindromeSubstringExtractor longestPalindromeSubstringExtractor;
  private final LongestSubstring longestSubstring;
  private final PinCracker pinCracker;
  private final RearrangingFruits rearrangingFruits;
  private final RomanNumberProcessor romanNumberProcessor;

  /**
   * Merges two integer arrays and calculates the median of the combined sorted
   * array.
   *
   * @param array1 the first array
   * @param array2 the second array
   * @return the median value
   */
  public double calculateMedian(int[] array1, int[] array2) {
    return arrayMediaCalculator.mergeAndCalculate(array1, array2);
  }

  /**
   * Calculates the maximum amount of water that can be contained between two
   * lines.
   *
   * @param heights an array of integers representing the heights of the lines
   * @return the maximum area of water that can be contained
   */
  public int calculateMaxWaterArea(int[] heights) {
    return containerWater.maxArea(heights);
  }

  /**
   * Counts the number of negative numbers in a 2D grid.
   *
   * @param grid the 2D grid of integers
   * @return the count of negative numbers
   */
  public int countNegatives(int[][] grid) {
    return countNegativesSortedMix.count(grid);
  }

  /**
   * Returns all possible letter combinations that phone digits could represent.
   *
   * @param digits a string containing digits 2-9
   * @return list of all possible letter combinations
   */
  public List<String> getLetterCombinations(String digits) {
    return letterCombinations.combine(digits);
  }

  /**
   * Merges k sorted linked lists into one sorted linked list.
   *
   * @param lists array of sorted linked lists
   * @return the merged sorted linked list
   */
  public ListNode mergeKSortedLists(ListNode[] lists) {
    return linkedListMerger.mergeKLists(lists);
  }

  /**
   * Finds the longest palindromic substring in the given string.
   *
   * @param input the input string
   * @return the longest palindromic substring
   */
  public String findLongestPalindrome(String input) {
    return longestPalindromeSubstringExtractor.longestPalindrome(input);
  }

  /**
   * Finds the length of the longest substring without repeating characters.
   *
   * @param input the input string
   * @return the length of the longest unique substring
   */
  public int findLongestUniqueSubstringLength(String input) {
    return longestSubstring.longestUniqueSubstringLength(input);
  }

  /**
   * Calculates the minimum cost to rearrange fruits between two baskets.
   *
   * @param basket1 the first basket of fruit costs
   * @param basket2 the second basket of fruit costs
   * @return the minimum cost, or -1 if impossible
   */
  public long rearrangeFruits(int[] basket1, int[] basket2) {
    return rearrangingFruits.rearrange(basket1, basket2);
  }

  /**
   * Cracks a numeric PIN from its MD5 hash by brute-force search.
   *
   * @param hash   the MD5 hash (hex string) of the target PIN
   * @param maxLen the maximum PIN length to search (default 5 if null)
   * @return the recovered PIN string, or null if not found
   */
  public String crackPin(String hash, Integer maxLen) {

    if (Objects.isNull(maxLen)) {
      return this.pinCracker.crack(hash, 5);
    }

    return pinCracker.crack(hash, maxLen.intValue());
  }

  /**
   * Converts a Roman numeral string to its integer value.
   *
   * @param roman the Roman numeral string (e.g., "MCMXCIV")
   * @return the integer value (e.g., 1994)
   * @throws IllegalArgumentException if the input is null or blank
   */
  public int romanToInt(String roman) {
    return romanNumberProcessor.romanToInt(roman);
  }

  /**
   * Converts an integer to its Roman numeral representation.
   *
   * @param number the integer to convert (1-3999)
   * @return the Roman numeral string (e.g., "MCMXCIV")
   * @throws IllegalArgumentException if number is out of range
   */
  public String intToRoman(int number) {
    return romanNumberProcessor.intToRoman(number);
  }

}
