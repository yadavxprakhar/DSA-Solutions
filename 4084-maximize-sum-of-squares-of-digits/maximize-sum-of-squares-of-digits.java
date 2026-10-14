class Solution {
    public String maxSumOfSquares(int num, int sum) {
        if (sum > num * 9)
            return "";

        int nines = sum / 9;
        int remain = sum % 9;

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < nines; i++) {
            result.append('9');
        }

        if (remain > 0) {
            result.append((char) (remain + '0'));
        }

        while (result.length() < num) {
            result.append('0');
        }

        return result.toString();
    }
}