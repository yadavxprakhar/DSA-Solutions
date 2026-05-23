class Solution {
    public static double minMaxDist(int[] stations, int k) {

        int n = stations.length;
        double low = 0;
        double high = 0;
        for (int i = 0; i < n - 1; i++) {
            high = Math.max(
                    high,
                    stations[i + 1] - stations[i]
            );
        }
        while ((high - low) > 1e-6) {
            double mid = (low + high) / 2.0;
            if (canPlace(stations, k, mid)) {
                high = mid;
            }
            else {
                low = mid;
            }
        }
        return high;
    }
    private static boolean canPlace(
            int[] stations,
            int k,
            double dist) {
        int count = 0;
        for (int i = 0; i < stations.length - 1; i++) {
            double gap =
                    stations[i + 1] - stations[i];

            count += (int)(gap / dist);

            if (gap % dist == 0) {
                count--;
            }
        }
        return count <= k;
    }
}