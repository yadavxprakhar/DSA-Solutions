class Solution {
    public int maxNumberOfBalloons(String text) {
        int freq[] = new int[26];
        for(int i=0; i<text.length(); i++){
           char ch = text.charAt(i);
           freq[ch-'a']++;
        }  
        int min = freq['b'-'a'];
        min = Math.min(min, freq[0]);
        min = Math.min(min, freq['n'-'a']);
        min = Math.min(min, freq['l'-'a']/2);
        min = Math.min(min, freq['o'-'a']/2);
        return min;
    }
}