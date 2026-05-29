class Solution {

    int[][] dp;

    public int validGroups(String s) {

        int maxSum = 9 * s.length();

        dp = new int[s.length()][maxSum + 1];

        for(int[] row : dp)
            Arrays.fill(row, -1);

        return dfs(s, 0, 0);
    }

    private int dfs(
            String s,
            int index,
            int prevSum) {

        if(index == s.length())
            return 1;

        if(dp[index][prevSum] != -1)
            return dp[index][prevSum];

        int count = 0;

        int currSum = 0;

        for(int i = index;
            i < s.length();
            i++) {

            currSum +=
                s.charAt(i) - '0';

            if(currSum >= prevSum) {

                count += dfs(
                            s,
                            i + 1,
                            currSum
                         );
            }
        }

        return dp[index][prevSum]
                = count;
    }
}