package at.mavila.exercises_january_2026.domain.collection;

/**
 * Domain model representing a node in a singly linked list.
 *
 * <p>
 * This class is used as a building block for linked list operations
 * such as merging sorted lists.
 * </p>
 *
 * @author mavila
 * @since January 2026
 */
public class ListNode {

  /**
   * The value stored in this node
   */
  public int val;

  /**
   * Reference to the next node in the list
   */
  public ListNode next;

  public ListNode(int val) {
    this.val = val;
    this.next = null;
  }

  public ListNode(int val, ListNode next) {
    this.val = val;
    this.next = next;
  }

  public ListNode() {
    this.val = 0;
    this.next = null;
  }

}
