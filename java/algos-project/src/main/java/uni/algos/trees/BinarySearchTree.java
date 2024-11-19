package uni.algos.trees;

public class BinarySearchTree<K extends Comparable<K>, V> extends TreeMap<K, V, BinarySearchTree<K, V>.Node> {
  class Node extends TreeEntry<K, V, Node> {
    public Node(K key, V value) {
      super(key, value);
    }
  }

  public Node createNode(K key, V value) {
    return new Node(key, value);
  }
}
