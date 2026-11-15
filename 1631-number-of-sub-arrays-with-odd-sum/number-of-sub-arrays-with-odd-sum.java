class Solution {
    public int numOfSubarrays(int[] arr) {
        int n = arr.length;
        int M = (int)1e9 + 7;

        int count = 0;
        int odd = 0;
        int even = 1;
        int sum = 0;

        for (int i = 0; i < n; i++) {
            sum += arr[i];

            if (sum % 2 == 0) { 
                count = (count + odd) % M;
                even++;
            } else { 
                count = (count + even) % M;
                odd++;
            }
        }

        return count;
    }
}