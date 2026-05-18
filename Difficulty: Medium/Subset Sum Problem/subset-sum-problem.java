class Solution {
    static Boolean isSubsetSum(int arr[], int sum) {
        return solve(0, sum, arr);
    }
    static boolean solve(int index,
                         int target,
                         int arr[]) {
        if (target == 0) {
            return true;
        }
        if (index == arr.length) {
            return false;
        }
        if (arr[index] <= target) {
            if (solve(index + 1,
                      target - arr[index],
                      arr)) {
                return true;
            }
        }
        return solve(index + 1,
                     target,
                     arr);
    }
}