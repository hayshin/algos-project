package uni.algos;
// Wrapper for getting all maps with names

import java.util.HashMap;
import java.util.Map;

import uni.algos.trees.*;
import uni.algos.hash_table.*;

public class Maps {
  public static HashMap<String, Map<Integer, Integer>> intMaps() {
    HashMap<String, Map<Integer, Integer>> intMaps = new HashMap<>();
    intMaps.put("bst", new BinarySearchTree<>());
    intMaps.put("avlt", new AVLTree<>());
    intMaps.put("rbt", new RedBlackTree<>());
    // intMaps.put("st", new SplayTree<>());
    intMaps.put("bt", new BTree<>());
    intMaps.put("ht", new HashTable<>());
    intMaps.put("htc", new HashTableCuckoo<>());
    intMaps.put("htm", new HashTableMurmur<>());
    intMaps.put("htoa", new HashTableOpenAddressing<>());
    return intMaps;
  }
  public static HashMap<String, Map<String, Integer>> strMaps() {
    HashMap<String, Map<String, Integer>> strMaps = new HashMap<>();
    strMaps.put("bst", new BinarySearchTree<>());
    strMaps.put("avlt", new AVLTree<>());
    strMaps.put("rbt", new RedBlackTree<>());
    // strMaps.put("st", new SplayTree<>());
    strMaps.put("bt", new BTree<>());
    strMaps.put("ht", new HashTable<>());
    strMaps.put("htc", new HashTableCuckoo<>());
    strMaps.put("htm", new HashTableMurmur<>());
    strMaps.put("htoa", new HashTableOpenAddressing<>());
    return strMaps;
  }
}
