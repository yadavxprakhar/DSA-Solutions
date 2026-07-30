class Solution {
    public int minimumPushes(String word) {
        int totalPushes = 0;
        for (int i = 0; i < word.length(); i++) {
            totalPushes += (i / 8) + 1;
        }
        return totalPushes;   
    }
}