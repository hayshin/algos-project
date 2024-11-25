package uni.algos.trees;

public class AVLTreeIter<K extends Comparable<K>, V> extends TreeMap<K, V, AVLTreeIter<K, V>.Node> {
  class Node extends TreeEntry<K, V, Node> {
    Node parent;
    byte balance = 0;

    Node(K key, V value) {
      super(key, value);
    }

    @Override
    public void setLeft(Node node) {
      this.left = node;
      node.parent = this;
    }

    @Override
    public void setRight(Node node) {
      this.right = node;
      node.parent = this;
    }
  }

  public Node createNode(K key, V value) {
    return new Node(key, value);
  }


  private void rotateLeft(Node p) {
    Node r = p.right;
    p.right = r.left;
    if (r.left != null)
      r.left.parent = p;
    r.parent = p.parent;
    if (p.parent == null)
      root = r;
    else if (p.parent.left == p)
      p.parent.left = r;
    else
      p.parent.right = r;
    r.left = p;
    p.parent = r;
  }

  /** From CLR */
  private void rotateRight(Node p) {
    Node l = p.left;
    p.left = l.right;
    if (l.right != null)
      l.right.parent = p;
    l.parent = p.parent;
    if (p.parent == null)
      root = l;
    else if (p.parent.right == p)
      p.parent.right = l;
    else
      p.parent.left = l;
    l.right = p;
    p.parent = l;
  } // Right rotate subtree rooted with y

  @Override
  public V put(K key, V value) {
    return put(root, key, value).value;
  }

  private Node put(Node node, K key, V value) {

    updateHeight(node);
    rebalance(node);
    return node;
  }

  public void remove(K key) {
    root = delete(root, key);
  }

  private Node delete(Node node, K key) {
    if (node == null) {
      return null; // Key not found, nothing to delete
    }

    int cmp = key.compareTo(node.getKey());

    if (cmp < 0) {
      // Key is smaller than current node's key, move to the left subtree
      node.left = delete(node.left, key);
    } else if (cmp > 0) {
      // Key is larger than current node's key, move to the right subtree
      node.right = delete(node.right, key);
    } else {
      // Node with the key found
      if (node.left == null || node.right == null) {
        // Node with only one child or no child
        Node temp = (node.left != null) ? node.left : node.right;

        if (temp == null) {
          // No child case
          node = null;
        } else {
          // One child case
          node = temp;
        }
      } else {
        // Node with two children
        Node temp = min(node.right); // Find the inorder successor

        // Copy the inorder successor's key and value to the current node
        K successorKey = temp.getKey();
        V successorValue = temp.getValue();

        // Replace current node's key and value with the successor's key and value
        node = new Node(successorKey, successorValue);

        // Delete the inorder successor
        node.right = delete(node.right, successorKey);
      }
    }

    // If the tree had only one node, return it
    if (node == null) {
      return null;
    }

    // Update height of the current node
    updateHeight(node);

    // Rebalance the node
    return rebalance(node);
  }
}
