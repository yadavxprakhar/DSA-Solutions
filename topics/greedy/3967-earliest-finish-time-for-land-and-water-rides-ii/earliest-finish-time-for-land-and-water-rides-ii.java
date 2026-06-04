class Solution {

    public int earliestFinishTime(int[] landStartTime,  int[] landDuration,  int[] waterStartTime,  int[] waterDuration) {
        long ans = Long.MAX_VALUE;
        ans = Math.min( ans, solve( landStartTime, landDuration, waterStartTime, waterDuration ) );
        ans = Math.min( ans, solve( waterStartTime, waterDuration, landStartTime, landDuration ) );
        return (int) ans;
    }
    private long solve( int[] firstStart, int[] firstDuration, int[] secondStart, int[] secondDuration) {
        int m = secondStart.length;
        int[][] rides = new int[m][2];
        for (int i = 0; i < m; i++) {
            rides[i][0] = secondStart[i];
            rides[i][1] = secondDuration[i];
        }
        Arrays.sort(rides, (a, b) -> a[0] - b[0]);
        int[] starts = new int[m];
        long[] prefMinDuration = new long[m];
        long[] suffMinStartPlusDuration = new long[m];
        for (int i = 0; i < m; i++) {
            starts[i] = rides[i][0];
        }
        prefMinDuration[0] = rides[0][1];
        for (int i = 1; i < m; i++) {
            prefMinDuration[i] = Math.min(prefMinDuration[i - 1], rides[i][1] );
        }
        suffMinStartPlusDuration[m - 1] = (long) rides[m - 1][0] + rides[m - 1][1];
        for (int i = m - 2; i >= 0; i--) {
            suffMinStartPlusDuration[i] =  Math.min( suffMinStartPlusDuration[i + 1], (long) rides[i][0] + rides[i][1] );
        }
        long answer = Long.MAX_VALUE;
        for (int i = 0; i < firstStart.length; i++) {
            long finish =  (long) firstStart[i] + firstDuration[i];
            int pos =upperBound(  starts, (int) finish );
            if (pos > 0) {
                answer = Math.min( answer, finish + prefMinDuration[pos - 1]);
            }
            if (pos < m) {
                answer = Math.min( answer, suffMinStartPlusDuration[pos] );
            }
        }
        return answer;
    }
    private int upperBound(int[] arr, int target) {
        int low = 0;
        int high = arr.length;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] <= target) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }
}