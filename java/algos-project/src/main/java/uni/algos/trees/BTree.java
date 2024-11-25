package uni.algos.trees;

import uni.algos.AbstractMap;

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
  public V get(Object k) {
    K key = toKey(k);
    return get(root, key);
  }


  private V get(Node node, K key) {
    int i = 0;
    while (i < node.numKeys && key.compareTo(node.keys[i]) > 0) {
      i++;
    }
    if (i < node.numKeys && key.compareTo(node.keys[i]) == 0) {
      return node.values[i];
    }
    if (node.isLeaf) {
      return null;
    } else {
      return get(node.children[i], key);
    }
  }

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

  private V insertNonFull(Node node, K key, V value) {
    int i = node.numKeys - 1;
    if (node.isLeaf) {
      while (i >= 0 && key.compareTo(node.keys[i]) < 0) {
        node.keys[i + 1] = node.keys[i];
        node.values[i + 1] = node.values[i];
        i--;
      }
      if (i >= 0 && key.compareTo(node.keys[i]) == 0) {
        V oldValue = node.values[i];
        node.values[i] = value;
        return oldValue;
      } else {
        node.keys[i + 1] = key;
        node.values[i + 1] = value;
        node.numKeys++;
        return null;
      }
    } else {
      while (i >= 0 && key.compareTo(node.keys[i]) < 0) {
        i--;
      }
      i++;
      if (node.children[i].numKeys == 2 * MIN_DEGREE - 1) {
        splitChild(node, i, node.children[i]);
        if (key.compareTo(node.keys[i]) > 0) {
          i++;
        }
      }
      return insertNonFull(node.children[i], key, value);
    }
  }

  @Override
  public int size() {
    return size(root);
  }

  private int size(Node name) {
    if (name == null) return 0;
    
    int count = name.numKeys;
    if (!name.isLeaf) {
      for (int i = 0; i <= name.numKeys; i++) {
        count += size(name.children[i]);
      }
    }
    return count;
  }

  @Override
  public V remove(Object key) {
    throw new UnsupportedOperationException("Remove not supported yet");
  }
}
