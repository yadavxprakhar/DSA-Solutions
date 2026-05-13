class Solution {

    public ArrayList<String> binstr(int n) {
        ArrayList<String> ans = new ArrayList<>();
        solve(0, n, "", ans);
        return ans;
    }
    private void solve(int index, int n, String str, ArrayList<String> ans) {
        if (index == n) {
            ans.add(str);
            return;
        }
        solve(index + 1, n, str + "0", ans);
        solve(index + 1, n, str + "1", ans);
    }
}