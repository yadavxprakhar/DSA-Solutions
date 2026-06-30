class Solution {
    public int numberOfSubstrings(String s) {
        int n = s.length();
        int[] last = new int[3]; 
        Arrays.fill(last, -1);
        int ans = 0;

        for (int i = 0; i < n; i++) {
            last[s.charAt(i) - 'a'] = i;
            int minLast = Math.min(last[0], Math.min(last[1], last[2]));
            ans += (1 + minLast);
        }

        return ans;
    }
}