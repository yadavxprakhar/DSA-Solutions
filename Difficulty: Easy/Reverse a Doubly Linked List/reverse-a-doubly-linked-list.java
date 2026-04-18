/*
class Node {
    int data;
    Node next;
    Node prev;

    Node(int data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}
*/
class Solution {
    public Node reverse(Node head) {
       Node temp = head;
       Stack <Integer> st = new Stack<>();
       while(temp!= null){
           st.push(temp.data);
           temp = temp.next;
       }
       Node temp1 = head;
       while(temp1!=null){
           temp1.data = st.peek();
           st.pop();
           temp1=temp1.next;
       }
       return head;
    }
}