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
    public ListNode sortList(ListNode head) {
    if(head==null || head.next==null) return head;
    ListNode temp = head;
    int n = len(head);
    int[] arr = new int[n];
    int i = 0;
    while(temp!=null){
         arr[i++]=temp.val;
         temp = temp.next;
    } 
    Arrays.sort(arr);  
    temp = head;
    int j=0;
    while(temp!=null){
        temp.val = arr[j];
        j++;
        temp = temp.next;
    } 
    return head;
    }
    private int len(ListNode head){
        ListNode temp = head;
        int cnt = 0;
        while(temp!=null){
            cnt++;
            temp = temp.next;
        }
        return cnt;
    }
}