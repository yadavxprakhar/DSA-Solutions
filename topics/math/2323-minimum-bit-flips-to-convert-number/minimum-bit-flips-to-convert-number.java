class Solution {
    public int minBitFlips(int start, int goal) {
       int cnt = 0;
       int  ans = start^goal;
       for(int i=0; i<32; i++){
        if((ans & (1 << i)) != 0){
            cnt = cnt+1;
        }
       }
       return cnt;    
    }
}