class Solution {
    public int[] assignTasks(int[] servers, int[] tasks) {
        int n = servers.length;
        int m = tasks.length;
        PriorityQueue<int[]> freeServers = new PriorityQueue<>(
            (a, b) -> a[0] != b[0] ? Integer.compare(a[0], b[0]) : Integer.compare(a[1], b[1])
        );


        PriorityQueue<long[]> busyServers = new PriorityQueue<>(
            (a, b) -> {
                if (a[0] != b[0]) return Long.compare(a[0], b[0]);
                if (a[1] != b[1]) return Long.compare(a[1], b[1]);
                return Long.compare(a[2], b[2]);
            }
        );

        for (int i = 0; i < n; i++) {
            freeServers.add(new int[]{servers[i], i});
        }

        int[] result = new int[m];

        for (int i = 0; i < m; i++) {
            while (!busyServers.isEmpty() && busyServers.peek()[0] <= i) {
                long[] cur = busyServers.poll();
                freeServers.add(new int[]{(int) cur[1], (int) cur[2]});
            }

            if (freeServers.isEmpty()) {
                long[] cur = busyServers.poll();
                result[i] = (int) cur[2];
                busyServers.add(new long[]{cur[0] + tasks[i], cur[1], cur[2]});
            } else {
                int[] cur = freeServers.poll();
                result[i] = cur[1];
                busyServers.add(new long[]{(long) i + tasks[i], cur[0], cur[1]});
            }
        }

        return result;
    }
}