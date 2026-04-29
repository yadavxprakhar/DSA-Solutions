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
    public ListNode reverseKGroup(ListNode head, int k) {
    ListNode temp =head;
    ListNode nextNode = null;
    ListNode prevNode = null;
    while(temp!= null){
        ListNode kthNode = findKthNode(temp, k);
        if(kthNode == null){
            if(prevNode != null){
                prevNode.next = temp;
            }
            break;
        }
        nextNode = kthNode.next;
        kthNode.next = null;
        reverse(temp);
        if(temp == head){
            head = kthNode;
        }
        else{
            prevNode.next= kthNode;
        }
        prevNode = temp;
        temp = nextNode;
    }  
    return head;  
    }
    private ListNode reverse(ListNode head) {
       ListNode temp = head;
       ListNode prev = null;
       while(temp!= null){
          ListNode front = temp.next;
          temp.next = prev;
          prev = temp;
          temp = front;
        }   
         return prev;
    }
    private ListNode findKthNode(ListNode head, int k){
        int cnt = 0;
        ListNode temp = head;
        while(temp!=null){
            cnt++;
            if(cnt == k){
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }
}