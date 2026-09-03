class Solution {
    public boolean uniformArray(int[] nums1) {
        int minEl = Integer.MAX_VALUE;
        for (int num : nums1) {
            minEl = Math.min(minEl, num);
        }
        if (minEl % 2 == 1) {   
            return true;       
        }
        for (int num : nums1) {
            if (num % 2 == 1) {
                return false;
            }
        }
        return true;           
    }
}