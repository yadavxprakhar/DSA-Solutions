class Solution {
    public int minAddToMakeValid(String s) {
        int size = 0; 
        int open = 0; 

        for (char ch : s.toCharArray()) {
            if (ch == '(') {
                size++;
            } else if (size > 0) {
                size--; 
            } else {
                open++; 
            }
        }

        return open + size; 
    }
}