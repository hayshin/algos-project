package uni.algos;
// Wrapper for getting all maps with names

import java.util.HashMap;
import java.util.Map;

import uni.algos.trees.BinarySearchTree;
import uni.algos.trees.AVLTree;

public class Maps {
  public static HashMap<String, Map<Integer, Integer>> intMaps() {
    HashMap<String, Map<Integer, Integer>> intMaps = new HashMap<>();
    intMaps.put("bst", new BinarySearchTree<>());
    intMaps.put("avl", new AVLTree<>());
    return intMaps;
  }
  public static HashMap<String, Map<String, Integer>> strMaps() {
    HashMap<String, Map<String, Integer>> strMaps = new HashMap<>();
    strMaps.put("bst", new BinarySearchTree<>());
    strMaps.put("avl", new AVLTree<>());
    return strMaps;
  }
}
