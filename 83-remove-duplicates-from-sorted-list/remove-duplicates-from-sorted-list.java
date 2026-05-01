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
    public ListNode deleteDuplicates(ListNode head) {
    ListNode temp = head;
    while(temp!= null && temp.next!=null){
        ListNode nextNode = temp.next;
        while(nextNode!=null && nextNode.val == temp.val){
            nextNode = nextNode.next;
        }
        temp.next = nextNode;
        temp = temp.next;
    }  
    return head;  
    }
}