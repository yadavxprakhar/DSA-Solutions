class Solution {

    public void sortStack(Stack<Integer> st) {
        if (!st.isEmpty()) {
            int temp = st.pop();
            sortStack(st);
            insert(st, temp);
        }
    }

    private void insert(Stack<Integer> stack, int temp) {
        if (stack.isEmpty() || stack.peek() <= temp) {
            stack.push(temp);
            return;
        }
        int val = stack.pop();
        insert(stack, temp);
        stack.push(val);
    }
}