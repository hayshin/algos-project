package uni.algos;
// Wrapper for getting all maps with names

import java.util.Map;

import uni.algos.trees.*;
import uni.algos.hash_table.*;

public class Maps {
  public static Map<Integer, Integer> intMap(String name) {
   return switch (name) {
      case "avlt" -> new AVLTree<>();
      case "rbt" -> new RedBlackTree<>();
      case "bt" -> new BTree<>();
      case "ht" -> new HashTable<>();
      case "htc" -> new HashTableCuckoo<>();
      case "htm" -> new HashTableMurmur<>();
      case "htoa" -> new HashTableOpenAddressing<>();
      default -> new HashTable<>();
    };
  }

  public static Map<String, Integer> strMap(String name) {
   return switch (name) {
      case "avlt" -> new AVLTree<>();
      case "rbt" -> new RedBlackTree<>();
      case "bt" -> new BTree<>();
      case "ht" -> new HashTable<>();
      case "htc" -> new HashTableCuckoo<>();
      case "htm" -> new HashTableMurmur<>();
      case "htoa" -> new HashTableOpenAddressing<>();
      default -> new HashTable<>();
    };
  }
}
