class Solution {
    int[] jumps;
    public int maxJumps(int[] arr, int d) {
    int n = arr.length;
    jumps = new int[n];

    Arrays.fill(jumps, -1);
    for(int i=0; i<n; i++){
        jumps[i] = dfs(i, arr, d);
    }  
    int max =1;
    for(int i=0; i<n; i++){
        max = Math.max(max, jumps[i]);
    } 
    return max;
    }
    int dfs(int ind, int arr[], int d){
        if(jumps[ind]!= -1)
            return jumps[ind];
        jumps[ind]= 1;
        for(int i =ind-1; i>=0 && ind-i <=d; i--){
            if(arr[i] < arr[ind]){
                jumps[i] = dfs(i, arr, d);
                jumps[ind] = Math.max(jumps[ind], jumps[i]+1);
            }
            else break;
        }
        for(int i =ind+1; i<arr.length && i-ind <=d; i++){
            if(arr[i] < arr[ind]){
                jumps[i] = dfs(i, arr, d);
                jumps[ind] = Math.max(jumps[ind], jumps[i]+1);
            }
            else break;
        }
        return jumps[ind];
    }
}