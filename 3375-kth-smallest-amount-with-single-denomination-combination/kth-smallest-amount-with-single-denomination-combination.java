class Solution {
    private int[] coins;
    private int k;
    public long findKthSmallest(int[] coins, int k) {
        this.coins = coins;
        this.k = k;
        long left = 1;
        long right = (long) k * findMin(coins);
        while (left < right) {
            long mid = left + (right - left) / 2;
            if (count(mid) >= k) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }
    private long count(long x) {
        long total = 0;
        int n = coins.length;
        for (int mask = 1; mask < (1 << n); mask++) {
            long lcmValue = 1;
            int selected = 0;
            boolean tooLarge = false;
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    selected++;
                    lcmValue = lcm(lcmValue, coins[i]);
                    if (lcmValue > x) {
                        tooLarge = true;
                        break;
                    }
                }
            }
            if (tooLarge) continue;
            long multiples = x / lcmValue;
            if (selected % 2 == 1) {
                total += multiples;
            } else {
                total -= multiples;
            }
        }
        return total;
    }
    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }
    private long lcm(long a, long b) {
        return a / gcd(a, b) * b;
    }
    private int findMin(int[] coins) {
        int min = coins[0];
        for (int coin : coins) {
            min = Math.min(min, coin);
        }
        return min;
    }
}