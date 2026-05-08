class Solution {

    public List<String> validParenthesis(String s) {

        List<String> result = new ArrayList<>();

        if (s == null) {
            return result;
        }

        Queue<String> queue = new LinkedList<>();
        HashSet<String> visited = new HashSet<>();

        queue.offer(s);
        visited.add(s);

        boolean found = false;

        while (!queue.isEmpty()) {

            String curr = queue.poll();

            if (isValid(curr)) {
                result.add(curr);
                found = true;
            }

            if (found) {
                continue;
            }

            for (int i = 0; i < curr.length(); i++) {

                char ch = curr.charAt(i);

                if (ch != '(' && ch != ')') {
                    continue;
                }

                String next =
                        curr.substring(0, i) +
                        curr.substring(i + 1);

                if (!visited.contains(next)) {
                    visited.add(next);
                    queue.offer(next);
                }
            }
        }

        Collections.sort(result);

        return result;
    }

    private boolean isValid(String s) {

        int count = 0;

        for (char ch : s.toCharArray()) {

            if (ch == '(') {
                count++;
            }

            else if (ch == ')') {

                count--;

                if (count < 0) {
                    return false;
                }
            }
        }

        return count == 0;
    }
}