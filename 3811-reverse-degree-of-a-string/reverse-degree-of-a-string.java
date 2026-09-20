class Solution {
    public int reverseDegree(String s) {
        int sum = 0;
        int i = 1;
        for(char c : s.toCharArray()){
           int d = 26 - (c - 'a');
           sum = sum + (d * i);
           i++;
        }
        return sum;
    }
}