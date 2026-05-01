/*
class Node {
    int data;  // value stored in node
    Node next;
    Node prev;

    Node(int value) {
        data = value;
        next = null;
        prev = null;
    }
}
*/
class Solution {
    Node removeDuplicates(Node head) {
        // code here
        Node temp = head;
        while(temp!=null && temp.next != null){
            Node nextNode = temp.next;
            while(nextNode!= null && nextNode.data == temp.data  ){
                nextNode = nextNode.next;
            }
            temp.next = nextNode;
            if(nextNode!= null){
                nextNode.prev = temp;
            }
            temp = temp.next;
        }
        return head;
    }
}