class Solution {
    public String evaluate(String s, List<List<String>> knowledge) {
        int n = s.length();
        Map<String, String> mp = new HashMap<>();

        for (List<String> vec : knowledge) {
            mp.put(vec.get(0), vec.get(1));
        }

        StringBuilder result = new StringBuilder();
        int i = 0;

        while (i < n) {
            if (s.charAt(i) == '(') {
                int j = s.indexOf(")", i + 1);
                String temp = s.substring(i + 1, j);
                result.append(mp.getOrDefault(temp, "?"));
                i = j;
            } else {
                result.append(s.charAt(i));
            }

            i++;
        }
        return result.toString();
    }
}