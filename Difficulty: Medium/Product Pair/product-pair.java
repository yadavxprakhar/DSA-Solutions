import java.util.HashSet;
class Solution {
    public boolean isProduct(int[] arr, long target) {
        HashSet<Integer> seen = new HashSet<>();
        
        for (int x : arr) {
            if (x == 0) {
                if (target == 0 && seen.contains(0)) return true;
            } else if (target % x == 0) {
                long required = target / x;
                if (required >= Integer.MIN_VALUE && required <= Integer.MAX_VALUE) {
                    if (seen.contains((int) required)) { // Safe explicit downcast
                        return true;
                    }
                }
            }
            seen.add(x);
        }
        return false;
    }
}
