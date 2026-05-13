class Solution {

    public boolean checkSubsequenceSum(int[] arr, int k) {
        return solve(0, 0, arr, k);
    }
    private boolean solve(int index, int sum, int[] arr, int k) {
        if (sum == k) {
            return true;
        }
        if (index == arr.length || sum > k) {
            return false;
        }
        if (solve(index + 1, sum + arr[index], arr, k)) {
            return true;
        }
        return solve(index + 1, sum, arr, k);
    }
}