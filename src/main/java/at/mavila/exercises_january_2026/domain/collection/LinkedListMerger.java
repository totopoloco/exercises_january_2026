package at.mavila.exercises_january_2026.domain.collection;

import java.util.Objects;

import org.springframework.stereotype.Component;

/**
 * Domain service for merging multiple sorted linked lists.
 *
 * <p>
 * This component merges k sorted linked lists into a single sorted linked list
 * using a sequential merge approach.
 * </p>
 *
 * <h2>Algorithm</h2>
 * <p>
 * Iteratively merges lists one by one using the "dummy head" technique:
 * </p>
 * <ol>
 * <li>Start with the first non-null list as the base</li>
 * <li>Merge each subsequent list into the base</li>
 * <li>When merging two lists, use two pointers to compare and link nodes</li>
 * </ol>
 *
 * <h2>Complexity</h2>
 * <ul>
 * <li><b>Time:</b> O(N*k) where N is total nodes and k is number of lists</li>
 * <li><b>Space:</b> O(1) - merging is done in-place</li>
 * </ul>
 *
 * @author mavila
 * @since January 2026
 */
@Component
public class LinkedListMerger {

  /**
   * Merges k sorted linked lists into one sorted linked list.
   *
   * @param lists array of sorted linked lists
   * @return the merged sorted linked list, or null if input is null/empty
   * @throws IllegalArgumentException if the input array is null
   */
  public ListNode mergeKLists(ListNode[] lists) {

    if (Objects.isNull(lists)) {
      throw new IllegalArgumentException("Input lists cannot be null");
    }

    ListNode merged = null;
    for (int n = 0; n < lists.length; n++) {
      if (Objects.nonNull(lists[n])) {
        if (Objects.isNull(merged)) {
          merged = lists[n];
        } else {
          merged = mergeTwoLists(merged, lists[n]);
        }
      }
    }

    return merged;
  }

  /**
   * Merges two sorted linked lists into a single sorted linked list.
   *
   * @param source1 the first sorted linked list to merge
   * @param source2 the second sorted linked list to merge
   * @return a new sorted linked list containing all nodes from both input lists
   */
  private ListNode mergeTwoLists(final ListNode source1, final ListNode source2) {
    // Handle edge cases
    if (Objects.isNull(source1)) {
      return source2;
    }
    if (Objects.isNull(source2)) {
      return source1;
    }

    // Working pointers to traverse both input lists
    ListNode currentStateOfSource1 = source1;
    ListNode currentStateOfSource2 = source2;

    // Determine the head of the merged list
    ListNode head;
    if (currentStateOfSource1.val <= currentStateOfSource2.val) {
      head = currentStateOfSource1;
      currentStateOfSource1 = currentStateOfSource1.next;
    } else {
      head = currentStateOfSource2;
      currentStateOfSource2 = currentStateOfSource2.next;
    }

    ListNode tail = head;

    // Traverse both lists while both have remaining nodes
    while (Objects.nonNull(currentStateOfSource1) && Objects.nonNull(currentStateOfSource2)) {
      if (currentStateOfSource1.val <= currentStateOfSource2.val) {
        tail = tail.next = currentStateOfSource1;
        currentStateOfSource1 = currentStateOfSource1.next;
      } else {
        tail = tail.next = currentStateOfSource2;
        currentStateOfSource2 = currentStateOfSource2.next;
      }
    }

    // Append the remaining nodes from the non-empty list
    if (Objects.nonNull(currentStateOfSource1)) {
      tail.next = currentStateOfSource1;
    } else {
      tail.next = currentStateOfSource2;
    }

    return head;
  }

}
