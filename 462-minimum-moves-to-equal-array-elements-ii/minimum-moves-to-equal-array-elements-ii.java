class Solution {
    private final Random rand = new Random();

    private void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }

    private void quickSelect(int[] nums, int k) {
        int left = 0, right = nums.length - 1;

        while (left < right) {
            int pivot = nums[left + rand.nextInt(right - left + 1)];

            int lt = left, i = left, gt = right;
            while (i <= gt) {
                if (nums[i] < pivot)      swap(nums, lt++, i++);
                else if (nums[i] > pivot) swap(nums, i, gt--);
                else                      i++;
            }

            if (k < lt)       right = lt - 1;
            else if (k > gt)  left = gt + 1;
            else              return;
        }
    }

    public int minMoves2(int[] nums) {
        int n = nums.length;

        quickSelect(nums, n / 2); 

        int mid = nums[n / 2];

        int moves = 0;
        for (int i = 0; i < n / 2; i++) {
            moves += Math.abs(nums[i] - mid);
            moves += Math.abs(nums[n - i - 1] - mid);
        }

        return moves;
    }
}