/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public int[] nodesBetweenCriticalPoints(ListNode head) {
        List<Integer> critical = new ArrayList<>();

        ListNode prev = head;
        ListNode curr = head.next;

        int index = 1;
        while (curr != null && curr.next != null) {
            if ((curr.val > prev.val && curr.val > curr.next.val) ||
                (curr.val < prev.val && curr.val < curr.next.val)) {
                critical.add(index);
            }
            prev = curr;
            curr = curr.next;
            index++;
        }
        if (critical.size() < 2) {
            return new int[]{-1, -1};
        }
        int minDist = Integer.MAX_VALUE;
        for (int i = 1; i < critical.size(); i++) {
            minDist = Math.min(
                minDist,
                critical.get(i) - critical.get(i - 1)
            );
        }
        int maxDist =
            critical.get(critical.size() - 1)
            - critical.get(0);

        return new int[]{minDist, maxDist};
    }
}