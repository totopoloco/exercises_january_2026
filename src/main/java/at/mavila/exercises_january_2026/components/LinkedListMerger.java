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
     * @param merged   the first sorted linked list to merge
     * @param listNode the second sorted linked list to merge
     * @return a new sorted linked list containing all nodes from both input lists
     */
    private ListNode mergeTwoLists(final ListNode merged, final ListNode listNode) {
        // Create a dummy node to serve as the starting point of the merged list.
        // This simplifies handling the head of the result list.
        ListNode dummy = new ListNode(0);

        // Tail pointer keeps track of the last node in the merged list
        ListNode tail = dummy;

        // Create working pointers for both input lists to traverse them
        ListNode tempMerged = merged;
        ListNode tempListNode = listNode;

        // Traverse both lists while both have remaining nodes
        while (Objects.nonNull(tempMerged) && Objects.nonNull(tempListNode)) {
            // Compare current nodes and append the smaller one to the merged list
            if (tempMerged.val <= tempListNode.val) {
                // tempMerged's value is smaller or equal, so append tempMerged's node
                tail.next = tempMerged;
                // Move tempMerged pointer to the next node in its list
                tempMerged = tempMerged.next;
                // Advance the tail pointer to the newly added node
                tail = tail.next;
                continue;
            }

            // tempListNode's value is smaller, so append tempListNode's node
            tail.next = tempListNode;
            // Move tempListNode pointer to the next node in its list
            tempListNode = tempListNode.next;
            // Advance the tail pointer to the newly added node
            tail = tail.next;

        }

        // At this point, at least one list is exhausted.
        // Append the remaining nodes from the non-empty list (if any).
        if (Objects.nonNull(tempMerged)) {
            // tempMerged still has nodes, append the rest of tempMerged
            tail.next = tempMerged;
        } else {
            // tempListNode still has nodes (or is null), append the rest of tempListNode
            tail.next = tempListNode;
        }

        // Return the merged list, skipping the dummy head node
        return dummy.next;
    }

}
