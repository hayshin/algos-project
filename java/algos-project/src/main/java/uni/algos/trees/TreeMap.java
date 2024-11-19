package uni.algos.trees;

import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;

public abstract class TreeMap<K extends Comparable<K>, V, E extends TreeEntry<K, V, E>> implements Map<K, V> {

  protected abstract E createNode(K key, V value);

  protected E root;

  private E rightOf(E node) {
    return node == null ? null : node.right;
  }

  private E leftOf(E node) {
    return node == null ? null : node.left;
  }

  public E first(E node) {
    while (leftOf(node) != null)
      node = node.left;
    return node;
  }

  public E last(E node) {
    while (rightOf(node) != null)
      node = node.right;
    return node;
  }

  @Override
  public V get(Object k) {
    if (!(k instanceof Comparable))
      throw new ClassCastException();
    @SuppressWarnings("unchecked")
    K key = (K) k;
    return value(get(root, key));
  }

  protected V value(E node) {
    return node == null ? null : node.value;
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
    return put(root, key, value);
  }

  protected V put(E node, K key, V value) {
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
        return node.setValue(value);
      }
    }

    if (cmp > 0)
      parent.right = createNode(key, value);
    else if (cmp < 0)
      parent.left = createNode(key, value);

    return null;
  }

  @Override
  public void putAll(Map<? extends K, ? extends V> map) {
    for (Entry<? extends K, ? extends V> entry : map.entrySet()) {
      put(entry.getKey(), entry.getValue());
    }
  }

  @Override
  public V remove(Object k) {
    if (!(k instanceof Comparable))
      throw new ClassCastException();
    @SuppressWarnings("unchecked")
    K key = (K) k;
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

    E successor = first(rightOf(node));
    successor = (successor != null) ? successor : last(leftOf(node));

    if (successor != null) {
      remove(node, successor.key); // delete successor
      node.key = successor.key; // replace node key, value with successor key, value
      node.value = successor.value;
    } else { // node has no childs so just set link to it to null
      if (cmp > 0)
        parent.right = null;
      else if (cmp < 0)
        parent.left = null;
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
  public boolean containsKey(Object k) {
    return get(k) != null;
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
    return node.value == value || containsValue(node.right, value) || containsValue(node.left, value);
  }

  @Override
  public Set<Entry<K, V>> entrySet() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Set<K> keySet() {
    throw new UnsupportedOperationException();
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
  public boolean isEmpty() {
    return root == null;
  }

  @Override
  public void clear() {
    root = null;
  }
}
