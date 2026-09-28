class Solution {
    public String reverseParentheses(String s) {
        Stack<StringBuilder> stack = new Stack<>();
        StringBuilder current = new StringBuilder();

        for (char ch : s.toCharArray()) {

            if (ch == '(') {
                stack.push(current);
                current = new StringBuilder();

            } else if (ch == ')') {
                current.reverse();
                current = stack.pop().append(current);

            } else {
                current.append(ch);
            }
        }

        return current.toString();
    }
}