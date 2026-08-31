class Solution {
    public int minimumDeletions(int[] nums) {
        int n = nums.length;
        int minIndex = findMin(nums);
        int maxIndex = findMax(nums);
        if (minIndex > maxIndex) {
            int temp = minIndex;
            minIndex = maxIndex;
            maxIndex = temp;
        }
        int left = maxIndex + 1;
        int right = n - minIndex;
        int both1 = minIndex + 1 + (n - maxIndex);
        int both2 = maxIndex + 1 + (n - minIndex);
        return Math.min(
            Math.min(left, right),
            Math.min(both1, both2)
        );
    }
    private int findMin(int[] nums){
        int min = Integer.MAX_VALUE;
        int index = -1;
        for(int i=0; i<nums.length; i++){
            if(nums[i] <= min){
                index = i;
                min = nums[i];
            }
        }
        return index;
    }
    private int findMax(int[] nums){
        int max = Integer.MIN_VALUE;
        int index = -1;
        for(int i=0; i<nums.length; i++){
            if(nums[i] >= max){
                index = i;
                max = nums[i];
            }
        }
        return index;
    }
}