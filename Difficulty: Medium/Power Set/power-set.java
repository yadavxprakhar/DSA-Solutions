// User function Template for Java

class Solution {
    public List<String> AllPossibleStrings(String s) {
        List<String> result = new ArrayList<>();
        generateSubsequences(s, 0, "", result);
        Collections.sort(result);
        
        return result;
    }
    
    private void generateSubsequences(String s, int index, String current, List<String> result) {
        if (index == s.length()) {
            if (!current.isEmpty()) {
                result.add(current);
            }
            return;
        }
        generateSubsequences(s, index + 1, current + s.charAt(index), result);
        generateSubsequences(s, index + 1, current, result);
    }
}