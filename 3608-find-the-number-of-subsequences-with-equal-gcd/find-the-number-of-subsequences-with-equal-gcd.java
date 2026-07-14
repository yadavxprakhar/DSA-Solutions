class Solution {
    int mod = 1_000_000_007;
    int dp[][][];
    public int subsequencePairCount(int[] nums) {
        int n = nums.length, max = 0;
        for(int num:nums)
            max = Math.max(num, max);
        dp = new int[n][max+1][max+1];
        for(int i=0; i<n; i++) {
            for(int a[] : dp[i])
                Arrays.fill(a, -1);
        }
        return helper(0,0,0,nums);
    }

    int helper(int i, int seq1, int seq2, int nums[]) {
        if(i==nums.length) {
            if(seq1!=seq2 || (seq1==0 && seq2==0))
                return 0;
            else return 1;
        }

        if(dp[i][seq1][seq2]!=-1)
            return dp[i][seq1][seq2];
        long take1 = helper(i+1, gcd(nums[i], seq1), seq2, nums);
        long take2 = helper(i+1, seq1, gcd(nums[i], seq2), nums);
        long skip = helper(i+1, seq1, seq2, nums);

        long ans = (take1%mod + take2%mod + skip%mod)%mod;
        return dp[i][seq1][seq2] = (int)ans;
    }
    int gcd(int a, int b) {
        if(b==0)
            return a;
        return gcd(b, a%b);
    }
}