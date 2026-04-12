/*
Definition for Linked List
class Node{
    int data;
    Node next;
    Node(int a){  data = a; next = null; }
}
*/
class Solution {
    public int getCount(Node head) {
        // code here
        int count= 0;
        Node temp = head;
        while(temp != null){
            temp=temp.next;
            count++;
        }
        return count;
    }
}