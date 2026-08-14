class Solution {
    public int maximumLengthSubstring(String s) {
        int[] freq = new int[26];
        int l = 0;
        int max = 0;
        for (int r = 0; r < s.length(); r++) {
            freq[s.charAt(r) - 'a']++;

            while (freq[s.charAt(r) - 'a'] > 2) {
                freq[s.charAt(l) - 'a']--;
                l++;
            }
            max = Math.max(max, r - l + 1);
        }
        return max;
    }
}