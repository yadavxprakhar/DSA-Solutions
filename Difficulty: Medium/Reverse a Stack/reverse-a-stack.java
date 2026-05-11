class Solution {
    public static void reverseStack(Stack<Integer> st) {
        if (st.isEmpty()) return;
        int topVal = st.pop();
        reverseStack(st);
        insertAtBottom(st, topVal);
    }
    private static void insertAtBottom(Stack<Integer> st, int val) {
        if (st.isEmpty()) {
            st.push(val);
            return;
        }
        int topVal = st.pop();
        insertAtBottom(st, val);
        st.push(topVal);
    }
}
