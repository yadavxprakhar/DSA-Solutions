/*

Definition for singly Link List Node
class Node
{
    int data;
    Node next,prev;

    Node(int x){
        data = x;
        next = null;
        prev = null;
    }
}

You can also use the following for printing the link list.
Node.printList(Node node);
*/

class Solution {
    public static ArrayList<ArrayList<Integer>> findPairsWithGivenSum(int target, Node head) {

        ArrayList<ArrayList<Integer>> al = new ArrayList<>();

        if (head == null) return al;

        Node left = head;
        Node right = findTail(head);

        while (left != null && right != null && left != right && left != right.next) {

            int sum = left.data + right.data;

            if (sum == target) {

                ArrayList<Integer> pair = new ArrayList<>();
                pair.add(left.data);
                pair.add(right.data);
                al.add(pair);

                left = left.next;     
                right = right.prev;

            } else if (sum < target) {
                left = left.next;
            } else {
                right = right.prev;
            }
        }

        return al;
    }

    private static Node findTail(Node head) {
        Node temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }
        return temp;
    }
}
