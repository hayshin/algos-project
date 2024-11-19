package uni.algos.trees;

import java.util.Map;
import java.util.Set;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Tree<K extends Comparable<K>, V> implements Map<K, V> {

  private TreeEntry<K, V> root;

  private TreeEntry<K, V> rightOf(TreeEntry<K, V> node) {
    return node == null ? null : node.right;
  }

  private TreeEntry<K, V> leftOf(TreeEntry<K, V> node) {
    return node == null ? null : node.left;
  }

  public TreeEntry<K, V> first(TreeEntry<K, V> node) {
    while (leftOf(node) != null)
      node = node.left;
    return node;
  }

  public TreeEntry<K, V> last(TreeEntry<K, V> node) {
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
    return get(root, key);
  }

  private V get(TreeEntry<K, V> node, K key) {
    while (node != null) {
      int cmp = key.compareTo(node.getKey());
      if (cmp > 0)
        node = node.right;
      else if (cmp < 0)
        node = node.left;
      else
        return node.value;
    }
    return null;
  }

  @Override
  public V put(K key, V value) {
    return put(root, key, value);
  }

  private V put(TreeEntry<K, V> node, K key, V value) {
    TreeEntry<K, V> parent = node;
    int cmp = 0;

    while (node != null) {
      cmp = key.compareTo(node.getKey());
      parent = node;
      if (cmp > 0)
        node = node.right;
      else if (cmp < 0)
        node = node.left;
      else {
        return node.setValue(value);
      }
    }

    cmp = key.compareTo(parent.key);
    if (cmp > 0)
      parent.right = new TreeEntry<K, V>(key, value);
    else if (cmp < 0)
      parent.left = new TreeEntry<K, V>(key, value);
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

  private V remove(TreeEntry<K, V> node, K key) {
    TreeEntry<K, V> parent = node;
    int cmp = 0;

    while (node != null) {
      cmp = key.compareTo(node.key);
      parent = node;
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

    TreeEntry<K, V> replaceNode = first(rightOf(node));
    replaceNode = (replaceNode != null) ? replaceNode : last(leftOf(node));

    if (replaceNode != null) { // node has no child and replaceNode not found if null
      replaceNode.left = node.left;
      replaceNode.right = node.right;
      remove(node, replaceNode.key); // delete replaceNode
    }

    if (cmp > 0)
      parent.right = replaceNode;
    else if (cmp < 0)
      parent.left = replaceNode;

    return node.value;
  }

  @Override
  public int size() {
    return size(root);
  }

  private int size(TreeEntry<K, V> node) {
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

  private boolean containsValue(TreeEntry<K, V> node, V value) {
    if (node == null)
      return false;
    return node.value == value || containsValue(node.right, value) || containsValue(node.left, value);
  }

  @Override
  public Set<Entry<K, V>> entrySet() {
    Set<Map.Entry<K, V>> set = new TreeSet();
    set.root = traverse(root);
    return set;
  }

  private TreeNode<TreeEntry<K,V>> traverse(TreeEntry<K, V> entry) {
    if (entry == null)
      return null;
    TreeNode<TreeEntry<K,V>> node = new TreeNode<>(entry);
    node.left = traverse(entry.left);
    node.right = traverse(entry.right);
    return node;
  }

  @Override
  public Set<K> keySet() {
    TreeSet<K> set = new TreeSet<K>();
    set.addAll(root);
    return set;
  }

  @Override
  public Collection<V> values() {
    ArrayList<V> values = new ArrayList<>();
    traverseValues(root, values);
    return values;
  }

  private void traverseValues(TreeEntry<K, V> node, ArrayList<V> values) {
    if (node == null)
      return;
    traverseValues(node.left, values);
    values.add(node.value);
    traverseValues(node.right, values);
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

class TreeNode<E extends Comparable<E>> {
  public E key;
  public TreeNode<E> left, right;

  public TreeNode(E key) {
    this.key = key;
  }
}

class TreeEntry<K extends Comparable<K>, V> extends TreeNode<K>
    implements Map.Entry<K, V>, Comparable<TreeEntry<K, V>> {
  public V value;
  public TreeEntry<K, V> left, right;

  public TreeEntry(K key, V value) {
    super(key);
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

  public int compareTo(TreeEntry<K, V> node) {
    return node.key.compareTo(key);
  }
}

class TreeSet<E extends Comparable<E>> implements Set<E> {

  public TreeNode<E> root;

  private TreeNode<E> rightOf(TreeNode<E> node) {
    return node == null ? null : node.right;
  }

  private TreeNode<E> leftOf(TreeNode<E> node) {
    return node == null ? null : node.left;
  }

  @Override
  public void clear() {
    root = null;
  }

  @Override
  public boolean contains(Object o) {
    @SuppressWarnings("unchecked")
    E e = (E) o;
    return contains(root, e);
  }

  public boolean contains(TreeNode<E> node, E key) {
    if (node == null)
      return false;
    return node.key == key || contains(node.right, key) || contains(node.left, key);
  }

  @Override
  public boolean containsAll(Collection<?> coll) {
    for (Object object : coll) {
      if (!contains(object))
        return false;
    }
    return true;
  }

  @Override
  public boolean retainAll(Collection<?> coll) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Iterator<E> iterator() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Object[] toArray() {
    Object[] array = new Object[size()];
    toArray(root, array, 0);
    return array;
  }

  @Override
  public <T> T[] toArray(T[] array) {
    toArray(root, array, 0);
    return array;
  }

  private int toArray(TreeNode<E> node, Object[] array, int index) {
    if (node == null)
      return index;
    index = toArray(node.left, array, index);
    array[index] = node.key;
    index = toArray(node.right, array, index);
    return index + 1;
  }

  public boolean isEmpty() {
    return root == null;
  }

  public TreeNode<E> first(TreeNode<E> node) {
    while (leftOf(node) != null)
      node = node.left;
    return node;
  }

  public TreeNode<E> last(TreeNode<E> node) {
    while (rightOf(node) != null)
      node = node.right;
    return node;
  }

  @Override
  public int size() {
    return size(root);
  }

  public int size(TreeNode<E> node) {
    if (node == null)
      return 0;
    return 1 + size(node.left) + size(node.right);
  }

  @Override
  public boolean add(E value) {
    return add(root, value);
  }

  public boolean add(TreeNode<E> node, E key) {
    TreeNode<E> parent = node;
    int cmp = 0;

    while (node != null) {
      cmp = key.compareTo(node.key);
      parent = node;
      if (cmp > 0)
        node = node.right;
      else if (cmp < 0)
        node = node.left;
      else {
        return false;
      }
    }

    cmp = key.compareTo(parent.key);
    if (cmp > 0)
      parent.right = new TreeNode<E>(key);
    else if (cmp < 0)
      parent.left = new TreeNode<E>(key);
    return true;
  }

  @Override
  public boolean addAll(Collection<? extends E> coll) {
    boolean result = true;
    for (E key : coll) {
      if (!add(key))
        result = false;
    }
    return result;
  }

  public <T extends TreeNode<E>> boolean addAll(T node) {
    root = node;
    return true;
  }

  @Override
  public boolean remove(Object object) {
    if (!(object instanceof Comparable))
      throw new ClassCastException();
    @SuppressWarnings("unchecked")
    E key = (E) object;
    return remove(root, key);
  }

  public boolean remove(TreeNode<E> node, E key) {
    TreeNode<E> parent = node;
    int cmp = 0;

    while (node != null) {
      cmp = key.compareTo(node.key);
      parent = node;
      if (cmp > 0)
        node = node.right;
      else if (cmp < 0)
        node = node.left;
      else {
        break;
      }
    }

    if (node == null)
      return false; // node not found

    TreeNode<E> replaceNode = first(rightOf(node));
    replaceNode = (replaceNode != null) ? replaceNode : last(leftOf(node));

    if (replaceNode != null) { // node has no child and replaceNode not found if null
      replaceNode.left = node.left;
      replaceNode.right = node.right;
      remove(node, replaceNode.key); // delete replaceNode
    }

    if (cmp > 0)
      parent.right = replaceNode;
    else if (cmp < 0)
      parent.left = replaceNode;

    return true;
  }

  @Override
  public boolean removeAll(Collection<?> coll) {
    boolean result = true;
    for (Object object : coll) {
      if (!remove(object))
        result = false;
    }
    return result;
  }
}
