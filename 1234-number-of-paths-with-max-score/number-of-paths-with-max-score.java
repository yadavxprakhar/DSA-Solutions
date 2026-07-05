class Solution {
    int n;
    int MOD = 1_000_000_007;
    int[][] tScore;
    int[][] tPaths;
    private int getIntFromChar(char ch) {
        return ch != 'S' ? ch - '0' : 0;
    }
    private boolean isValid(int i, int j, List<String> board) {
        return i >= 0 && i < n && j >= 0 && j < n && board.get(i).charAt(j) != 'X';
    }
    private int[] solve(int i, int j, List<String> board) {
        char here = board.get(i).charAt(j);
        if (here == 'E')
            return new int[]{0, 1};
        if (here == 'X')
            return new int[]{0, 0}; 
        if (tScore[i][j] != -1)
            return new int[]{tScore[i][j], tPaths[i][j]};
        int upScore = 0,   upPaths = 0;
        int leftScore = 0, leftPaths = 0;
        int diagScore = 0, diagPaths = 0;
        char ch = here;

        if (isValid(i - 1, j, board)) {
            int[] r = solve(i - 1, j, board);
            upScore = r[0];
            upPaths = r[1];
            if (upPaths > 0)
                upScore += getIntFromChar(ch);
        }
        if (isValid(i, j - 1, board)) {
            int[] r = solve(i, j - 1, board);
            leftScore = r[0];
            leftPaths = r[1];
            if (leftPaths > 0)
                leftScore += getIntFromChar(ch);
        }
        if (isValid(i - 1, j - 1, board)) {
            int[] r = solve(i - 1, j - 1, board);
            diagScore = r[0];
            diagPaths = r[1];
            if (diagPaths > 0)
                diagScore += getIntFromChar(ch);
        }

        int bestScore, bestPaths;
        if (upScore == leftScore && leftScore == diagScore) {
            bestScore = upScore;
            bestPaths = upPaths + leftPaths + diagPaths;
        } else if (upScore == leftScore) {
            bestScore = upScore;
            bestPaths = upPaths + leftPaths;
            if (diagScore > bestScore || (diagScore == bestScore && diagPaths > bestPaths)) {
                bestScore = diagScore; bestPaths = diagPaths;
            }
        } else if (leftScore == diagScore) {
            bestScore = leftScore;
            bestPaths = leftPaths + diagPaths;
            if (upScore > bestScore || (upScore == bestScore && upPaths > bestPaths)) {
                bestScore = upScore; bestPaths = upPaths;
            }
        } else {
            bestScore = upScore; bestPaths = upPaths;
            if (leftScore > bestScore || (leftScore == bestScore && leftPaths > bestPaths)) {
                bestScore = leftScore; bestPaths = leftPaths;
            }
            if (diagScore > bestScore || (diagScore == bestScore && diagPaths > bestPaths)) {
                bestScore = diagScore; bestPaths = diagPaths;
            }
        }

        tScore[i][j] = bestScore;
        tPaths[i][j] = (int)(((long) bestPaths) % MOD);
        return new int[]{tScore[i][j], tPaths[i][j]};
    }
    public int[] pathsWithMaxScore(List<String> board) {
        n = board.size();
        tScore = new int[n][n];
        tPaths = new int[n][n];
        for (int[] row : tScore) Arrays.fill(row, -1);

        int[] result = solve(n - 1, n - 1, board);
        return new int[]{result[0], result[1]};
    }
}
