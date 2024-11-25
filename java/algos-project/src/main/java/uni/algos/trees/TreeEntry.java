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

  @Override
  public K getKey() {
    return this.key;
  }

  public void setKey(K key) {
    this.key = key;
  }

  @Override
  public V getValue() {
    return this.value;
  }

  @Override
  public V setValue(V newValue) {
    V oldValue = this.value;
    value = newValue;
    return oldValue;
  }

  public void setLeft(E node) {
    this.left = node;
  }

  public E getLeft() {
    return left;
  }

  public E getRight() {
    return right;
  }

  public void setRight(E node) {
    this.right = node;
  }

  @Override
  public int compareTo(E node) {
    return this.key.compareTo(node.key);
  }
}
