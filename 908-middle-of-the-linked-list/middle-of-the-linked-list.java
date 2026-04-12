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
    public ListNode middleNode(ListNode head) {
    int n = lengthLL(head);
    int mid = (n/2)+1;
    ListNode temp = head;
    int cnt =0;
    while(temp!= null){
        cnt++;
        if(cnt == mid){
            return temp;
        }
        temp = temp.next;
    }
    return null;
    }
    public int lengthLL(ListNode head){
        ListNode temp = head;
        int count =0;
        while(temp != null){
            count++;
            temp= temp.next;
        }
        return count;
    }
}