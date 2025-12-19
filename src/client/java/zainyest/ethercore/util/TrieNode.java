package zainyest.ethercore.util;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.LinkedList;

public record TrieNode(@Nullable TrieNode parent, HashMap<Character, TrieNode> children, LinkedList<String> techniqueKeys) {
}
