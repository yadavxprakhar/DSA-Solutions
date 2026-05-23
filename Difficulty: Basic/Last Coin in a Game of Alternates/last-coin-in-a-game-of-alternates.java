class Solution {
    public int coin(int[] arr) {
        int left = 0;
        int right = arr.length - 1;
        while (left < right) {
            if (arr[left] >= arr[right]) {
                left++;
            }
            else {

                right--;
            }
        }
        return arr[left];
    }
}