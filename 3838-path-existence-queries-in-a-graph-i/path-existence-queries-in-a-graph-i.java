class Solution {
    int parent[];
    public boolean[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {
        parent = new int[n];
        for(int i=0; i<n ;i++) {
            parent[i]=i; }                        
        for(int i=0; i<n-1; i++) {
            int num1 = nums[i], num2 = nums[i+1];  

            if(Math.abs(num1 - num2) <= maxDiff) {
                union(i, i+1);                     
            }
        }
        boolean ans[] = new boolean[queries.length];
        int index = 0;
        for(int q[] : queries) {
            int x = q[0];
            int y = q[1];

            ans[index++] = (find(x)==find(y));     
        }
        return ans;
    }
    private int find(int x) {
        if(parent[x]!=x)
            parent[x] = find(parent[x]);           
        return parent[x];
    }
    private void union(int x, int y) {
        parent[find(x)] = find(y);                
    }
}