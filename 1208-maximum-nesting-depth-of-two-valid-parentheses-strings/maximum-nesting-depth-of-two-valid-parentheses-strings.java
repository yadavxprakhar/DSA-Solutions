class Solution {
    public int[] maxDepthAfterSplit(String seq) {
        int[] ans = new int[seq.length()];
        for (int i = 0; i < seq.length(); ++i) {
            if (seq.charAt(i) == '(') {
                ans[i] = i % 2;
            } else {
                ans[i] = 1 - i % 2;
            }
        }
        return ans;
    }
}