class Solution {
    public boolean checkString(String s) {
        int countA = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'a')
                countA++;
        }
        for (int i = 0; i < countA; i++) {
            if (s.charAt(i) != 'a')
                return false;
        }

        return true;
    }
}