class Solution {
    public char processStr(String s, long k) {
        long len = 0;
        for (char ch : s.toCharArray()) {
            if (ch >= 'a' && ch <= 'z') len++;
            else if (ch == '#')         len = len * 2;
            else if (ch == '*')         { if (len > 0) len--; }
        }
        if (k >= len) return '.';
        for (int i = s.length() - 1; i >= 0; i--) {
            char ch = s.charAt(i);
            if (ch == '*') {
                len++;
            } else if (ch == '#') {
                len = len / 2;
                if (k >= len) k = k - len;
            } else if (ch == '%') {
                k = len - 1 - k;
            } else {
                len--;
                if (len == k) return ch;
            }
        }
        return '.';
    }
}