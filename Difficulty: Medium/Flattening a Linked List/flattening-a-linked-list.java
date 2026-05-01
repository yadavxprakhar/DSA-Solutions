/*
class Node {
    int data;
    Node next;
    Node bottom;

    Node(int x) {
        data = x;
        next = null;
        bottom = null;
    }
}
*/
class Solution {
    public Node flatten(Node root) {
    if(root==null || root.next == null){
        return root;
    }
    Node mergeRoot  = flatten(root.next);
    return merge(root, mergeRoot);
    }
    private Node merge(Node root, Node mergeRoot){
        Node dummy = new Node(-1);
        Node res = dummy;
        while(root != null  && mergeRoot !=null){
            if(root.data < mergeRoot.data){
                res.bottom = root;
                res = root;
                root = root.bottom;
            }
            else{
                res.bottom = mergeRoot;
                res = mergeRoot;
                mergeRoot = mergeRoot.bottom;
            }
            res.next = null;
        }
        if(root!=null) {
            res.bottom = root;
        }
        else{
            res.bottom = mergeRoot;
        }
        return dummy.bottom;
    }
}