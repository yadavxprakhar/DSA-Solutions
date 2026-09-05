class Solution {
    public int firstStableIndex(int[] nums, int k) {
        int n = nums.length;

        int[] minFromIndex = new int[n];
        int minEl = Integer.MAX_VALUE;
        for (int i = n - 1; i >= 0; i--) {
            minEl = Math.min(minEl, nums[i]);
            minFromIndex[i] = minEl;
        }
        int maxEl = 0;
        for (int i = 0; i < n; i++) {
            maxEl = Math.max(maxEl, nums[i]);

            if (maxEl - minFromIndex[i] <= k)
                return i;
        }
        return -1;
    }
}