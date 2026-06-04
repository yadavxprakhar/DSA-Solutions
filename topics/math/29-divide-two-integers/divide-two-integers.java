class Solution {
    public int divide(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE &&
            divisor == -1) {
            return Integer.MAX_VALUE;
        }
        long dvd = Math.abs((long) dividend);
        long dvs = Math.abs((long) divisor);
        int ans = 0;
        while (dvd >= dvs) {
            int shift = 0;
            while (dvd >= (dvs << (shift + 1))) {
                shift++;
            }
            ans += (1 << shift);
            dvd -= (dvs << shift);
        }
        if ((dividend < 0) ^ (divisor < 0)) {
            return -ans;
        }
        return ans;
    }
}