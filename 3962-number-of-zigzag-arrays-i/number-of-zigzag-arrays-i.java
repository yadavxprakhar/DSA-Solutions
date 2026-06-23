class Solution {
    public int zigZagArrays(int n, int l, int r) {
        final int MOD = 1_000_000_007;
        int m = r - l + 1; 
        long[] up   = new long[m]; 
        long[] down = new long[m]; 
        Arrays.fill(up, 1);
        Arrays.fill(down, 1);

        for (int i = 2; i <= n; i++) {
            long[] preDown = new long[m + 1]; 
            for (int j = 0; j < m; j++)
                preDown[j + 1] = (preDown[j] + down[j]) % MOD;

            
            long[] sufUp = new long[m + 1]; 
            for (int j = m - 1; j >= 0; j--)
                sufUp[j] = (sufUp[j + 1] + up[j]) % MOD;

            long[] newUp   = new long[m];
            long[] newDown = new long[m];
            for (int j = 0; j < m; j++) {
                newUp[j]   = preDown[j];
                newDown[j] = sufUp[j + 1];
            }

            up = newUp;
            down = newDown;
        }
        long ans = 0;
        for (int j = 0; j < m; j++)
            ans = (ans + up[j] + down[j]) % MOD;
        return (int) ans;
    }
}