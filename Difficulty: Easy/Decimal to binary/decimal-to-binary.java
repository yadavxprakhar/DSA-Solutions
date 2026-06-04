class Solution {
    static String decToBinary(int n) {
        if(n == 0) return "0";
        String res = "";
        while(n != 1) {
            if(n % 2 == 1) {
                res += "1";
            } else {
                res += "0";
            }
            n /= 2;
        }
        res += "1";
        return new StringBuilder(res).reverse().toString();
    }
}