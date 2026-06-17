class Solution {

    Integer[] dp;

    public int maximumJumps(int[] nums, int target) {

        dp = new Integer[nums.length];

        return helper(0, nums, target);
    }

    private int helper(int ind, int[] nums, int target) {

        if(ind == nums.length - 1)
            return 0;

        if(dp[ind] != null)
            return dp[ind];

        int jumps = -1;

        for(int i = ind + 1; i < nums.length; i++) {

            if(Math.abs(nums[i] - nums[ind]) <= target) {

                int next = helper(i, nums, target);

                if(next != -1)
                    jumps = Math.max(jumps, next + 1);
            }
        }

        return dp[ind] = jumps;
    }
}