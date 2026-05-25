class Solution {
    int minToggle(int[] arr) {
        int n = arr.length;
        int onesLeft = 0;
        int zerosRight = 0;
        for (int num : arr) {
            if (num == 0) {
                zerosRight++;
            }
        }
        int ans = zerosRight;
        for (int i = 0; i < n; i++) {
            if (arr[i] == 1) {
                onesLeft++;
            }
            else {
                zerosRight--;
            }
            ans = Math.min(ans, onesLeft + zerosRight);
        }
        return ans;
    }
}