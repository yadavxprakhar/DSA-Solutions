/*
// Definition for a Node.
class Node {
    public int val;
    public Node prev;
    public Node next;
    public Node child;
};
*/

class Solution {
    public Node flatten(Node head) {
    dllf(head);
    return head;   
    }
    private Node dllf(Node head){
        Node temp = null;
        while(head!= null){
            temp = head;
            if(head.child != null){
                Node tail = dllf(head.child);
                tail.next = head.next;
                if(head.next != null){
                    head.next.prev = tail;
                }
                head.next = head.child;
                head.child.prev = head;
                head.child = null;
            }
            else{
                head = head.next;
            }
        }
        return temp;
    }
}