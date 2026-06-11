class Solution {
    public int[] pivotArray(int[] nums, int pivot) {
        int n = nums.length;
        int[] ans = new int[n];
        int left = 0;
        int pivotCount = 0;
        for(int num : nums) {
            if(num < pivot)
                ans[left++] = num;
            else if(num == pivot)
                pivotCount++;
        }
        int right = n - 1;
        for(int i = n - 1; i >= 0; i--) {
            if(nums[i] > pivot) {
                ans[right--] = nums[i];
            }
        }
        while(pivotCount-- > 0) {
            ans[left++] = pivot;
        }
        return ans;
    }
}