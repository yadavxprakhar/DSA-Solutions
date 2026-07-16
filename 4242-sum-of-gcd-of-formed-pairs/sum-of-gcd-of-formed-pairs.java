class Solution {
    public long gcdSum(int[] nums) {
        int max = -1, n = nums.length;
        int prefixGCD[] = new int[n];
        for(int i=0; i<n; i++) {
            max = Math.max(nums[i], max);
            prefixGCD[i] = gcd(nums[i], max);
        }
        long ans = 0;
        Arrays.sort(prefixGCD);
        for(int i=0,j=n-1; i<j; i++,j--) {
            ans += gcd(prefixGCD[i], prefixGCD[j]);
        }

        return ans;
    }
    int gcd(int a, int b) {
        if(b==0)
            return a;
        return gcd(b, a%b);
    }
}