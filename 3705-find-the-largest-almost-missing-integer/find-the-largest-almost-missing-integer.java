class Solution {
    public int largestInteger(int[] nums, int k) {
        int[] count = new int[51];
        for (int i = 0; i <= nums.length - k; i++) {

            boolean[] seen = new boolean[51];
            for (int j = i; j < i + k; j++) {
                seen[nums[j]] = true;
            }
            for (int num = 0; num <= 50; num++) {
                if (seen[num]) {
                    count[num]++;
                }
            }
        }
        for (int num = 50; num >= 0; num--) {
            if (count[num] == 1) {
                return num;
            }
        }

        return -1;
    }
}