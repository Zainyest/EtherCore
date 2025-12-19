package zainyest.ethercore.util;

import zainyest.ethercore.gui.screen.ingame.TreeElementWidget;

import java.util.*;

/// Null-terminated word trie, implemented with TreeScreen in mind
public class Trie {

    private TrieNode root;

    public Trie(TrieNode root) {
        this.root = root;
    }


    public Trie(LinkedHashMap<String, TreeElementWidget> treeElements) {
        this.root = new TrieNode(null, new HashMap<>(), new LinkedList<>());
        for (Map.Entry<String, TreeElementWidget> entry : treeElements.entrySet()) {
            for (String word : entry.getValue().getTextContent().split("\\s")) {
                this.add(word.toLowerCase(Locale.ROOT), entry.getKey());
            }
        }
    }

    public void add(String word, String techniqueKey) {
        TrieNode cur = root;
        for (char c : word.toCharArray()) {
            if (!cur.children().containsKey(c)) {
                cur.children().put(c, new TrieNode(cur, new HashMap<>(), new LinkedList<>()));
            }
            cur.children().get(c).techniqueKeys().add(techniqueKey);
            cur = cur.children().get(c);
        }
    }

    /// key should be a single word without spaces, requires preprocessing for multiple search terms
    public LinkedList<String> getTechniqueKeys(String key) {
        return traverseTrie(key.toLowerCase(Locale.ROOT).toCharArray(), 0, this.root);
    }

    private LinkedList<String> traverseTrie(char[] chars, int i, TrieNode cur) {
        if (chars.length-1 == i && cur.children().containsKey(chars[i])) {
            return cur.children().get(chars[i]).techniqueKeys();
        }
        if (cur.children().containsKey(chars[i])) {
            return traverseTrie(chars, i + 1, cur.children().get(chars[i]));
        }
        return null;
    }
}
