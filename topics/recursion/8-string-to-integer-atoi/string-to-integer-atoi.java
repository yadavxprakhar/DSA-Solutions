class Solution {

    public int myAtoi(String s) {
        int i = skipSpaces(s, 0);

        if (i == s.length()) return 0;

        int sign = 1;

        
        if (s.charAt(i) == '-' || s.charAt(i) == '+') {
            sign = (s.charAt(i) == '-') ? -1 : 1;
            i++;
        }

        return helper(s, i, 0, sign);
    }

    private int skipSpaces(String s, int i) {
        if (i < s.length() && s.charAt(i) == ' ') {
            return skipSpaces(s, i + 1);
        }
        return i;
    }

    private int helper(String s, int i, long result, int sign) {

       
        if (i >= s.length() || !Character.isDigit(s.charAt(i))) {
            return (int)(sign * result);
        }

        int digit = s.charAt(i) - '0';

        result = result * 10 + digit;

        
        if (sign * result > Integer.MAX_VALUE) return Integer.MAX_VALUE;
        if (sign * result < Integer.MIN_VALUE) return Integer.MIN_VALUE;

        return helper(s, i + 1, result, sign);
    }
}