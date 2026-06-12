class Solution {
    int mod = 1_000_000_007;
    int[] depth;
    HashMap<Integer, List<Integer>> hmap;
    int[][] lift; 
    public int[] assignEdgeWeights(int[][] edges, int[][] queries) {
        int n = edges.length + 1;
        depth = new int[n + 1];
        hmap = new HashMap<>();
        lift = new int[n + 1][18]; 
        for (int[] e : edges) {
            int s = e[0], d = e[1];
            hmap.putIfAbsent(s, new ArrayList<>());
            hmap.putIfAbsent(d, new ArrayList<>());
            hmap.get(s).add(d);
            hmap.get(d).add(s);
        }
        Queue<Integer> q = new LinkedList<>();
        boolean[] visited = new boolean[n + 1];
        q.offer(1);
        visited[1] = true;
        lift[1][0] = 1; 
        while (!q.isEmpty()) {
            int curr = q.remove();
            List<Integer> children = hmap.get(curr);
            if (children == null) continue;
            for (int next : children) {
                if (visited[next]) continue;
                visited[next] = true;
                depth[next] = depth[curr] + 1;
                lift[next][0] = curr; 
                q.offer(next);
            }
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j < 18; j++) {
                lift[i][j] = lift[lift[i][j - 1]][j - 1];
            }
        }

        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int u = queries[i][0], v = queries[i][1];
            int dist = depth[u] + depth[v] - 2 * depth[lca(u, v)];
            ans[i] = (dist == 0) ? 0 : power(2, dist - 1);
        }

        return ans;
    }
    int lca(int u, int v) {
        if (depth[u] < depth[v]) return lca(v, u);
        int diff = depth[u] - depth[v];
        for (int j = 0; j < 18; j++) {
            if ((diff & 1) == 1)
                u = lift[u][j];
            diff >>= 1;
        }

        if (u == v) return u; 
        for (int j = 17; j >= 0; j--) {
            if (lift[u][j] != lift[v][j]) {
                u = lift[u][j];
                v = lift[v][j];
            }
        }

        return lift[u][0];
    }
    int power(int base, int exp) {
        long res = 1;
        long b = base % mod;
        while (exp > 0) {
            if ((exp & 1) == 1)
                res = res * b % mod;
            b = b * b % mod;
            exp >>= 1;
        }
        return (int) res;
    }
}