/*
// Definition for a Node.
class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}
*/

class Solution {
    public Node copyRandomList(Node head) {
    insert(head);
    connectRandom(head);
    return  deepCopy(head);   
    }
    private void insert(Node head){
        Node temp = head;
        while(temp!=null){
            Node nextE = temp.next;
            Node copy = new Node(temp.val);

            copy.next = nextE;
            temp.next = copy;

            temp = nextE;
        }
    }
    private void connectRandom(Node head){
        Node temp = head;
        while(temp!=null){
            Node copy = temp.next;
            if(temp.random != null){
                copy.random = temp.random.next;
            }
            else{
                copy.random = null;
            }
            temp = temp.next.next;
        }
    }
    private Node deepCopy(Node head){
        Node temp = head;
        Node dummy = new Node(-1);
        Node res = dummy;
        while(temp!=null){
            res.next = temp.next;
            res = res.next;

            temp.next = temp.next.next;
            temp = temp.next;
        }
        return dummy.next;
    }
}