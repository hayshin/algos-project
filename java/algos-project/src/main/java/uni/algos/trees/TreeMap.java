package uni.algos.trees;

import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;
import uni.algos.AbstractMap;

public abstract class TreeMap<K extends Comparable<K>, V, E extends TreeEntry<K, V, E>> extends AbstractMap<K, V> {

  protected abstract E createNode(K key, V value);

  protected E root;

  protected E rightOf(E node) {
    return node == null ? null : node.right;
  }

  protected E leftOf(E node) {
    return node == null ? null : node.left;
  }

  public E min(E node) {
    while (leftOf(node) != null)
      node = node.left;
    return node;
  }

  public E max(E node) {
    while (rightOf(node) != null)
      node = node.right;
    return node;
  }

  protected V value(E node) {
    return node == null ? null : node.value;
  }


  @Override
  public V get(Object k) {
    K key = toKey(k);
    return value(get(root, key));
  }

  protected E get(E node, K key) {
    while (node != null) {
      int cmp = key.compareTo(node.getKey());
      if (cmp > 0)
        node = node.right;
      else if (cmp < 0)
        node = node.left;
      else
        return node;
    }
    return null;
  }

  @Override
  public V put(K key, V value) {
    if (root == null) {
      root = createNode(key, value);
      return null;
    }
    return put(root, key, value).value;
  }

  protected E put(E node, K key, V value) {
    E parent = node;
    int cmp = 0;

    while (node != null) {
      parent = node;
      cmp = key.compareTo(node.key);
      if (cmp > 0)
        node = node.right;
      else if (cmp < 0)
        node = node.left;
      else {
        node.setValue(value);
        return node;
      }
    }

    E newNode = createNode(key, value);
    if (cmp < 0)
      parent.setLeft(newNode);
    else
      parent.setRight(newNode);

    return newNode;
  }

  @Override
  public V remove(Object k) {
    K key = toKey(k);
    return remove(root, key);
  }

  private V remove(E node, K key) {
    E parent = node;
    int cmp = 0;

    while (node != null) {
      parent = node;
      cmp = key.compareTo(node.key);
      if (cmp > 0)
        node = node.right;
      else if (cmp < 0)
        node = node.left;
      else {
        break;
      }
    }

    if (node == null)
      return null; // node not found

    E successor = min(rightOf(node));
    successor = (successor != null) ? successor : max(leftOf(node));

    if (successor != null) {
      remove(node, successor.key); // delete successor
      node.setKey(successor.key);
      node.setValue(successor.value);
    } else { // node has no childs so just set link to it to null
      if (cmp > 0)
        parent.setRight(null);
      else if (cmp < 0)
        parent.setLeft(null);
    }

    return node.value;
  }

  @Override
  public int size() {
    return size(root);
  }

  private int size(E node) {
    if (node == null)
      return 0;
    return 1 + size(node.left) + size(node.right);
  }


  @Override
  public boolean containsValue(Object v) {
    @SuppressWarnings("unchecked")
    V value = (V) v;
    return containsValue(root, value);
  }

  private boolean containsValue(E node, V value) {
    if (node == null)
      return false;
    return node.value == value || containsValue(node.left, value) || containsValue(node.right, value);
  }

  @Override
  public Collection<V> values() {
    ArrayList<V> values = new ArrayList<>();
    traverseValues(root, values);
    return values;
  }

  private void traverseValues(E node, ArrayList<V> values) {
    if (node == null)
      return;
    traverseValues(node.left, values);
    values.add(node.value);
    traverseValues(node.right, values);
  }

  public Object[] toArray() {
    Object[] array = new Object[size()];
    toArray(root, array, 0);
    return array;
  }

  public <T> T[] toArray(T[] array) {
    toArray(root, array, 0);
    return array;
  }

  private int toArray(E node, Object[] array, int index) {
    if (node == null)
      return index;
    index = toArray(node.left, array, index);
    array[index] = node.key;
    index = toArray(node.right, array, index);
    return index + 1;
  }

  @Override
  public void clear() {
    root = null;
  }
}
