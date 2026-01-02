package at.mavila.exercises_january_2026.components;

import java.util.Objects;

import org.springframework.stereotype.Component;

@Component
public class LinkedListMerger {

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
     * <p>
     * This method uses the "dummy head" technique to simplify edge case handling.
     * It iterates through both lists simultaneously, always selecting the node
     * with the smaller value to append to the result list. When one list is
     * exhausted, the remaining nodes from the other list are appended directly.
     * </p>
     *
     * <p>
     * <b>Time Complexity:</b> O(n + m) where n and m are the lengths of the two
     * lists.
     * </p>
     * <p>
     * <b>Space Complexity:</b> O(1) as we only use a constant amount of extra
     * space.
     * </p>
     *
     * @param source1 the first sorted linked list to merge
     * @param source2 the second sorted linked list to merge
     * @return a new sorted linked list containing all nodes from both input lists
     */
    private ListNode mergeTwoLists(final ListNode source1, final ListNode source2) {
        // Handle edge cases - if one list is empty, return the other
        if (Objects.isNull(source1)) {
            return source2;
        }
        if (Objects.isNull(source2)) {
            return source1;
        }

        // Working pointers to traverse both input lists
        ListNode currentStateOfSource1 = source1;
        ListNode currentStateOfSource2 = source2;

        // Determine the head of the merged list (the smaller first node)
        ListNode head;
        if (currentStateOfSource1.val <= currentStateOfSource2.val) {
            head = currentStateOfSource1;
            currentStateOfSource1 = currentStateOfSource1.next;
        } else {
            head = currentStateOfSource2;
            currentStateOfSource2 = currentStateOfSource2.next;
        }

        // Tail pointer keeps track of the last node in the merged list.
        // Note: `head` and `tail` initially point to the same node. As we append nodes
        // via `tail.next`, the linked list grows. Since `head` holds a reference to the
        // first node, it remains the entry point to the entire merged list.
        ListNode tail = head;

        // Traverse both lists while both have remaining nodes
        while (Objects.nonNull(currentStateOfSource1) && Objects.nonNull(currentStateOfSource2)) {

            if (currentStateOfSource1.val <= currentStateOfSource2.val) { // Compare only values of nodes and not entire
                                                                          // nodes
                // Append source1's node and advance tail in one step, so in the next iteration
                // we always have the correct tail
                tail = tail.next = currentStateOfSource1;
                currentStateOfSource1 = currentStateOfSource1.next; // We deplete source1 one by one
            } else {
                // Append source2's node and advance tail in one step, so in the next iteration
                // we always have the correct tail
                tail = tail.next = currentStateOfSource2;
                currentStateOfSource2 = currentStateOfSource2.next; // We deplete source2 one by one
            }
        }

        // At this point, at least one list is exhausted.
        // Append the remaining nodes from the non-empty list (if any).
        if (Objects.nonNull(currentStateOfSource1)) {
            // source1 still has nodes, append the rest of source1
            tail.next = currentStateOfSource1;
        } else {
            // source2 still has nodes (or is null), append the rest of source2
            tail.next = currentStateOfSource2;
        }

        // Return the merged list
        return head;
    }

}
