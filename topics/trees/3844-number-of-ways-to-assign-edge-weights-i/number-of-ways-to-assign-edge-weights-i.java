class Solution {
    static final long MOD = 1000000007L;
    public int assignEdgeWeights(int[][] edges) {
        int n = edges.length + 1;
        List<Integer>[] graph = new ArrayList[n + 1];
        for(int i = 0; i <= n; i++)
            graph[i] = new ArrayList<>();
        for(int[] e : edges) {
            graph[e[0]].add(e[1]);
            graph[e[1]].add(e[0]);
        }
        Queue<int[]> q = new LinkedList<>();
        boolean[] vis = new boolean[n + 1];
        q.offer(new int[]{1,0});
        vis[1] = true;
        int maxDepth = 0;
        while(!q.isEmpty()) {
            int[] cur = q.poll();
            int node = cur[0];
            int depth = cur[1];
            maxDepth = Math.max(maxDepth, depth);
            for(int nxt : graph[node]) {
                if(vis[nxt]) continue;
                vis[nxt] = true;
                q.offer(new int[]{nxt, depth + 1});
            }
        }
        return (int)powMod(2, maxDepth - 1);
    }
    private long powMod(long a, long b) {
        long res = 1;
        while(b > 0) {
            if((b & 1) == 1)
                res = (res * a) % MOD;
            a = (a * a) % MOD;
            b >>= 1;
        }
        return res;
    }
}