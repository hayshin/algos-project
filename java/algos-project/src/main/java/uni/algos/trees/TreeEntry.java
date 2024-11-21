package uni.algos.trees;

import java.util.Map;

public class TreeEntry<K extends Comparable<K>, V, E extends TreeEntry<K, V, E>>
    implements Map.Entry<K, V>, Comparable<E> {
  public K key;
  public V value;
  public E left, right;

  public TreeEntry(K key, V value) {
    this.key = key;
    this.value = value;
  }

  public K getKey() {
    return this.key;
  }

  public V getValue() {
    return this.value;
  }

  public V setValue(V newValue) {
    V oldValue = this.value;
    value = newValue;
    return oldValue;
  }

  public int compareTo(E node) {
    return this.key.compareTo(node.key);
  }
}
