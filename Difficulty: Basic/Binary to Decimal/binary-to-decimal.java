class Solution {
    public int binaryToDecimal(String b) {
        int m = b.length();
        int num = 0;
        int p2 = 1;
        for(int i = m - 1; i >= 0; i--) {
            if(b.charAt(i) == '1') {
                num += p2;
            }
            p2 *= 2;
        }
        return num;
    }
}