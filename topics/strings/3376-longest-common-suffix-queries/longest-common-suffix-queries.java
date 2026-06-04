class Solution {
    public int[] stringIndices(String[] words, String[] queries) {
        Trie trie = new Trie();

        int index = -1, smallest = Integer.MAX_VALUE;
        for(int i= 0; i<words.length; i++){
            trie.insert(words[i], i);
            if(words[i].length() < smallest){
                smallest = words[i].length();
                index =i;
            }
        } 
        int ans[] = new int[queries.length];
        for(int i=0; i<queries.length; i++){
            int ind = trie.prefix(queries[i]);
            ans[i] = (ind==-1) ? index : ind;
        } 
        return ans;   
    }
}
class TrieNode{
    int index;
    int len;
    TrieNode[] children;

    TrieNode(){
        index = -1;
        len = Integer.MAX_VALUE;
        children = new TrieNode[26];
    }
}
class Trie{
    TrieNode root;

    Trie(){
        root = new TrieNode();
    }

    void insert(String str, int ind){
        TrieNode node = root;
        int strlen = str.length();
        for(int i= strlen-1; i>=0; i--){
            int ch = str.charAt(i)-'a';
            if(node.children[ch]==null)
                node.children[ch] = new TrieNode();
            node = node.children[ch];

            if(strlen < node.len ||(strlen == node.len && ind < node.index)){
                node.len = strlen;
                node.index = ind;

            }
        }
    }
    int prefix(String query){
       TrieNode node = root;
       int ans = -1;

       for(int i=query.length()-1; i>=0; i--){
           int ch = query.charAt(i) - 'a';
           if(node.children[ch]==null)
               break;
           node = node.children[ch];
           ans = node.index; 
        }
        return ans;
    }
}


