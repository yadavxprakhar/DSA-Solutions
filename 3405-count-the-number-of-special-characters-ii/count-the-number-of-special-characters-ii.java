class Solution {
    public int numberOfSpecialChars(String word) {
        int count = 0;
        for(char ch = 'a'; ch <= 'z'; ch++) {
            int lastLower = -1;
            int firstUpper = Integer.MAX_VALUE;
            for(int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if(c == ch) {
                    lastLower = i;
                }
                if(c == Character.toUpperCase(ch)) {
                    firstUpper = Math.min(firstUpper, i);
                }
            }
            if(lastLower != -1 &&
               firstUpper != Integer.MAX_VALUE &&
               lastLower < firstUpper) {
                count++;
            }
        }
        return count;
    }
}