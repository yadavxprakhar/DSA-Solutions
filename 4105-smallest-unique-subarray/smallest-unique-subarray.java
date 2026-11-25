class Solution {

    long mod = 1000000007L;
    long base = 911382323L;

    public int smallestUniqueSubarray(int[] nums) {

        int n = nums.length;

        long[] pow = new long[n + 1];
        long[] hash = new long[n + 1];

        pow[0] = 1;

        for (int i = 1; i <= n; i++) {
            pow[i] = (pow[i - 1] * base) % mod;
        }

        for (int i = 0; i < n; i++) {
            hash[i + 1] =
                (hash[i] * base + nums[i] + 1) % mod;
        }

        int low = 1;
        int high = n;
        int ans = n;

        while (low <= high) {

            int mid = low + (high - low) / 2;

            if (hasUnique(nums, mid, hash, pow)) {
                ans = mid;
                high = mid - 1;
            }

            else {
                low = mid + 1;
            }
        }

        return ans;
    }

    private boolean hasUnique(int[] nums,
                              int len,
                              long[] hash,
                              long[] pow) {

        int n = nums.length;

        HashMap<Long, Integer> freq = new HashMap<>();

        for (int i = 0; i + len <= n; i++) {

            long curr =
                (hash[i + len]
                - (hash[i] * pow[len]) % mod
                + mod) % mod;

            freq.put(curr,
                     freq.getOrDefault(curr, 0) + 1);
        }

        for (int val : freq.values()) {
            if (val == 1) {
                return true;
            }
        }

        return false;
    }
}