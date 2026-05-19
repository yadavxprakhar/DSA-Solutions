class Solution {

    public int minSteps(int[] arr, int start, int end) {
        Queue<Integer> q = new LinkedList<>();
        int[] dist = new int[1000];
        Arrays.fill(dist, -1);
        q.offer(start);
        dist[start] = 0;
        while (!q.isEmpty()) {
            int curr = q.poll();
            if (curr == end) {
                return dist[curr];
            }
            for (int num : arr) {
                int next = (curr * num) % 1000;
                if (dist[next] == -1) {
                    dist[next] = dist[curr] + 1;
                    q.offer(next);
                }
            }
        }
        return -1;
    }
}