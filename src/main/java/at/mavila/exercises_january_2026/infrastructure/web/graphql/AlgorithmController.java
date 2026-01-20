package at.mavila.exercises_january_2026.infrastructure.web.graphql;

import java.util.ArrayList;
import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import at.mavila.exercises_january_2026.application.AlgorithmService;
import at.mavila.exercises_january_2026.domain.collection.ListNode;
import lombok.RequiredArgsConstructor;

/**
 * GraphQL Controller that exposes all algorithm operations as queries.
 *
 * <p>
 * This controller acts as a GraphQL adapter, translating GraphQL queries
 * into application service calls and converting domain models to
 * GraphQL-compatible
 * representations.
 * </p>
 *
 * <h2>Responsibilities</h2>
 * <ul>
 * <li>Handle GraphQL query resolution</li>
 * <li>Convert GraphQL input types to domain types</li>
 * <li>Convert domain results to GraphQL output types</li>
 * </ul>
 *
 * @author mavila
 * @since January 2026
 */
@Controller
@RequiredArgsConstructor
public class AlgorithmController {

  private final AlgorithmService algorithmService;

  @QueryMapping
  public double calculateMedian(@Argument List<Integer> array1, @Argument List<Integer> array2) {
    return algorithmService.calculateMedian(
        array1.stream().mapToInt(Integer::intValue).toArray(),
        array2.stream().mapToInt(Integer::intValue).toArray());
  }

  @QueryMapping
  public int calculateMaxWaterArea(@Argument List<Integer> heights) {
    return algorithmService.calculateMaxWaterArea(
        heights.stream().mapToInt(Integer::intValue).toArray());
  }

  @QueryMapping
  public int countNegatives(@Argument List<List<Integer>> grid) {
    int[][] gridArray = grid.stream()
        .map(row -> row.stream().mapToInt(Integer::intValue).toArray())
        .toArray(int[][]::new);
    return algorithmService.countNegatives(gridArray);
  }

  @QueryMapping
  public List<String> getLetterCombinations(@Argument String digits) {
    return algorithmService.getLetterCombinations(digits);
  }

  @QueryMapping
  public List<Integer> mergeKSortedLists(@Argument List<List<Integer>> lists) {
    // Convert List<List<Integer>> to ListNode[]
    ListNode[] listNodes = lists.stream()
        .map(this::createLinkedList)
        .toArray(ListNode[]::new);

    // Merge the lists
    ListNode merged = algorithmService.mergeKSortedLists(listNodes);

    // Convert back to List<Integer>
    return linkedListToList(merged);
  }

  @QueryMapping
  public String findLongestPalindrome(@Argument String input) {
    return algorithmService.findLongestPalindrome(input);
  }

  @QueryMapping
  public int findLongestUniqueSubstringLength(@Argument String input) {
    return algorithmService.findLongestUniqueSubstringLength(input);
  }

  @QueryMapping
  public long rearrangeFruits(@Argument List<Integer> basket1, @Argument List<Integer> basket2) {
    return algorithmService.rearrangeFruits(
        basket1.stream().mapToInt(Integer::intValue).toArray(),
        basket2.stream().mapToInt(Integer::intValue).toArray());
  }

  @QueryMapping
  public String crackPin(@Argument String hash, @Argument Integer maxLen) {
    return algorithmService.crackPin(hash, maxLen);
  }

  @QueryMapping
  public int romanToInt(@Argument String roman) {
    return algorithmService.romanToInt(roman);
  }

  @QueryMapping
  public String intToRoman(@Argument int number) {
    return algorithmService.intToRoman(number);
  }

  /**
   * Helper method to create a linked list from a list of integers.
   */
  private ListNode createLinkedList(List<Integer> values) {
    if (values == null || values.isEmpty()) {
      return null;
    }

    ListNode head = new ListNode(values.getFirst());
    ListNode current = head;

    for (int i = 1; i < values.size(); i++) {
      current.next = new ListNode(values.get(i));
      current = current.next;
    }

    return head;
  }

  /**
   * Helper method to convert a linked list back to a list of integers.
   */
  private List<Integer> linkedListToList(ListNode head) {
    List<Integer> result = new ArrayList<>();
    ListNode current = head;

    while (current != null) {
      result.add(current.val);
      current = current.next;
    }

    return result;
  }

}
