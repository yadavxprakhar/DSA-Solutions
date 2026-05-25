class Solution {
    public boolean canReach(String s, int minJump, int maxJump) {
        int n = s.length();
        int arr[] = new int[n];
        if(s.charAt(n-1)== '1')
            return false;   
        int cnt = 0;

        for(int i=0; i<n; i++){
            cnt += arr[i];

            if(i==0 || s.charAt(i)=='0' && cnt >0) {
                if(i+minJump < n )
                    arr[i+minJump] += 1;
                if(i+maxJump+1 < n)
                    arr[i+maxJump+1] -= 1;
            }
        }
        return cnt>0;
    }
}