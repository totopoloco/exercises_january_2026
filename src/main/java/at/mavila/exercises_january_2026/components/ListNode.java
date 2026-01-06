package at.mavila.exercises_january_2026.components;

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
