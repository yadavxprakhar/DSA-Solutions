/*
class Node {
    int data;
    Node next;

    Node(int d)
    {
        data = d;
        next = null;
    }
}*/

class Solution {
    public Node segregate(Node head) {
        if(head == null || head.next == null){
            return head;
        }
        int cnt0 = 0;
        int cnt1 = 0;
        int cnt2 = 0;
        Node temp = head;
        while(temp!= null){
            if(temp.data == 0) cnt0++;
            else if(temp.data == 1) cnt1++;
            else cnt2++;
            temp = temp.next;
        }
        temp = head;
        while(temp!=null){
            if(cnt0 != 0){
                temp.data = 0;
                cnt0--;
            }
            else if (cnt1 !=0){
                temp.data =1;
                cnt1--;
            }
            else{
                temp.data = 2;
                cnt2--;
            }
            temp = temp.next;
        }
        return head;
        
    }
}