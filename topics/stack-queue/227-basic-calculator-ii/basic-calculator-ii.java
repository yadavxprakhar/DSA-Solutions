class Solution {
    public int calculate(String s) {
        Stack<Integer> stack = new Stack<>();
        int num = 0;
        char sign = '+';
        for(int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if(Character.isDigit(ch)) {
                num = num * 10
                        + (ch - '0');
            }
            if((!Character.isDigit(ch) && ch != ' ') || i == s.length() - 1) {
                if(sign == '+') {
                    stack.push(num);
                }
                else if(sign == '-') {
                    stack.push(-num);
                }
                else if(sign == '*') {
                    stack.push( stack.pop() * num );
                }
                else if(sign == '/') { 
                    stack.push( stack.pop() / num);
                }
                sign = ch;
                num = 0;
            }
        }
        int ans = 0;
        while(!stack.isEmpty()) {
            ans += stack.pop();
        }
        return ans;
    }
}