class Solution {
    public int maximumElementAfterDecrementingAndRearranging(int[] arr) {
        int n = arr.length;
        int[] count = new int[n + 1];
        for (int a : arr) count[Math.min(a, n)]++;
        int ans = 1;
        for (int i = 2; i < count.length; i++) {
            int next = ans + count[i];
            ans = Math.min(i, next);
        }
        return ans;
    }
}