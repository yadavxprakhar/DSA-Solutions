class Solution {

    public int strStr(String haystack, String needle) {
        int n = haystack.length();
        int m = needle.length();
        if(m > n) return -1;
        long base = 31;
        long mod = 1000000007;
        long needleHash = 0;
        long windowHash = 0;
        long power = 1;
        for(int i = 0; i < m; i++) {
            needleHash = (needleHash * base + (needle.charAt(i) - 'a' + 1)) % mod;
            windowHash = (windowHash * base + (haystack.charAt(i) - 'a' + 1)) % mod;
            if(i < m - 1)
                power = (power * base) % mod;
        }
        for(int i = 0; i <= n - m; i++) {
            if(windowHash == needleHash) {
                if(haystack.substring(i, i + m).equals(needle))
                    return i;
            }
            if(i < n - m) {
                windowHash = (windowHash - (haystack.charAt(i) - 'a' + 1) * power % mod + mod) % mod;
                windowHash = (windowHash * base + (haystack.charAt(i + m) - 'a' + 1)) % mod;
            }
        }
        return -1;
    }
}