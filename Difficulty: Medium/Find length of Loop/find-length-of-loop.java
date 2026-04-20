/*
class Node {
    int data;
    Node next;

    Node(int x) {
        data = x;
        next = null;
    }
}
*/

class Solution {
    public int lengthOfLoop(Node head) {
       Node slow = head;
       Node fast = head;
       while(fast!= null && fast.next!= null){
           slow = slow.next;
           fast = fast.next.next;
           if(slow == fast){
               return fullLength(slow, fast);
           }
       }
       return 0;
        
    }
    private int fullLength(Node slow, Node fast){
        int cnt = 1;
        slow = slow.next;
        while(slow != fast){
            cnt++;
            slow = slow.next;
            
        }
        return cnt;
        
    }
}