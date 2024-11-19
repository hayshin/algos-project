package uni.algos.trees;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class BTree<K extends Comparable<K>, V> extends AbstractMap<K, V> {
  private static final int MIN_DEGREE = 2; // Minimum degree (defines the range for number of keys)

  private class Node {
    int numKeys;
    K[] keys;
    V[] values;
    Node[] children;
    boolean isLeaf;

    @SuppressWarnings("unchecked")
    Node(boolean isLeaf) {
      this.isLeaf = isLeaf;
      this.keys = (K[]) new Comparable[2 * MIN_DEGREE - 1];
      this.values = (V[]) new Object[2 * MIN_DEGREE - 1];
      this.children = (Node[]) new BTree.Node[2 * MIN_DEGREE];
      this.numKeys = 0;
    }
  }

  private Node root;

  /**
   * Retrieves the value mapped to the given key.
   */
  @Override
  public V get(Object key) {
    K k = (K) key;
    return search(root, k);
  }

  private V search(Node x, K key) {
    int i = 0;
    while (i < x.numKeys && key.compareTo(x.keys[i]) > 0) {
      i++;
    }
    if (i < x.numKeys && key.compareTo(x.keys[i]) == 0) {
      return x.values[i];
    }
    if (x.isLeaf) {
      return null;
    } else {
      return search(x.children[i], key);
    }
  }

  /**
   * Inserts the key-value pair into the B-tree.
   */
  @Override
  public V put(K key, V value) {
    if (root == null) {
      root = new Node(true);
      root.keys[0] = key;
      root.values[0] = value;
      root.numKeys = 1;
      return null;
    }
    if (root.numKeys == 2 * MIN_DEGREE - 1) {
      Node s = new Node(false);
      s.children[0] = root;
      splitChild(s, 0, root);
      root = s;
    }
    return insertNonFull(root, key, value);
  }

  private void splitChild(Node parent, int index, Node y) {
    Node z = new Node(y.isLeaf);
    z.numKeys = MIN_DEGREE - 1;

    System.arraycopy(y.keys, MIN_DEGREE, z.keys, 0, MIN_DEGREE - 1);
    System.arraycopy(y.values, MIN_DEGREE, z.values, 0, MIN_DEGREE - 1);

    if (!y.isLeaf) {
      System.arraycopy(y.children, MIN_DEGREE, z.children, 0, MIN_DEGREE);
    }

    y.numKeys = MIN_DEGREE - 1;

    System.arraycopy(parent.children, index + 1, parent.children, index + 2, parent.numKeys - index);
    parent.children[index + 1] = z;

    System.arraycopy(parent.keys, index, parent.keys, index + 1, parent.numKeys - index);
    System.arraycopy(parent.values, index, parent.values, index + 1, parent.numKeys - index);
    parent.keys[index] = y.keys[MIN_DEGREE - 1];
    parent.values[index] = y.values[MIN_DEGREE - 1];
    parent.numKeys++;
  }

  private V insertNonFull(Node x, K key, V value) {
    int i = x.numKeys - 1;
    if (x.isLeaf) {
      while (i >= 0 && key.compareTo(x.keys[i]) < 0) {
        x.keys[i + 1] = x.keys[i];
        x.values[i + 1] = x.values[i];
        i--;
      }
      if (i >= 0 && key.compareTo(x.keys[i]) == 0) {
        V oldValue = x.values[i];
        x.values[i] = value;
        return oldValue;
      } else {
        x.keys[i + 1] = key;
        x.values[i + 1] = value;
        x.numKeys++;
        return null;
      }
    } else {
      while (i >= 0 && key.compareTo(x.keys[i]) < 0) {
        i--;
      }
      i++;
      if (x.children[i].numKeys == 2 * MIN_DEGREE - 1) {
        splitChild(x, i, x.children[i]);
        if (key.compareTo(x.keys[i]) > 0) {
          i++;
        }
      }
      return insertNonFull(x.children[i], key, value);
    }
  }

  /**
   * Returns the number of key-value mappings in this map.
   */
  @Override
  public int size() {
    return size(root);
  }

  private int size(Node x) {
    if (x == null) {
      return 0;
    }
    int count = x.numKeys;
    if (!x.isLeaf) {
      for (int i = 0; i <= x.numKeys; i++) {
        count += size(x.children[i]);
      }
    }
    return count;
  }

  /**
   * Returns a {@link Set} view of the mappings contained in this map.
   */
  @Override
  public Set<Map.Entry<K, V>> entrySet() {
    Set<Map.Entry<K, V>> set = new TreeSet<>();
    traverse(root, set);
    return set;
  }

  private void traverse(Node x, Set<Map.Entry<K, V>> set) {
    if (x == null) {
      return;
    }
    int i;
    for (i = 0; i < x.numKeys; i++) {
      if (!x.isLeaf) {
        traverse(x.children[i], set);
      }
      set.add(new AbstractMap.SimpleEntry<>(x.keys[i], x.values[i]));
    }
    if (!x.isLeaf) {
      traverse(x.children[i], set);
    }
  }

  /**
   * Removal operation is not supported in this implementation.
   */
  @Override
  public V remove(Object key) {
    throw new UnsupportedOperationException("Remove not supported yet");
  }
}
