package uni.algos.trees;

public class SplayTree<K extends Comparable<K>, V> extends BinarySearchTree<K,V> {
  private Node root;

  public SplayTree() {
    this.root = null;
  }

  public boolean isEmpty() {
    return root == null;
  }

  private Node rotateLeft(Node x) {
    Node y = x.right;
    x.right = y.left;
    y.left = x;
    return y;
  }

  private Node rotateRight(Node x) {
    Node y = x.left;
    x.left = y.right;
    y.right = x;
    return y;
  }

  private Node splay(Node node, K key) {
    if (node == null || node.key.equals(key)) {
      return node;
    }

    // Key lies in the left subtree
    if (key.compareTo(node.key) < 0) {
      if (node.left == null)
        return node;

      // Zig-Zig (Left Left)
      if (key.compareTo(node.left.key) < 0) {
        node.left.left = splay(node.left.left, key);
        node = rotateRight(node);
      }
      // Zig-Zag (Left Right)
      else if (key.compareTo(node.left.key) > 0) {
        node.left.right = splay(node.left.right, key);
        if (node.left.right != null) {
          node.left = rotateLeft(node.left);
        }
      }
      return (node.left == null) ? node : rotateRight(node);
    }

    // Key lies in the right subtree
    else {
      if (node.right == null)
        return node;

      // Zag-Zag (Right Right)
      if (key.compareTo(node.right.key) > 0) {
        node.right.right = splay(node.right.right, key);
        node = rotateLeft(node);
      }
      // Zag-Zig (Right Left)
      else if (key.compareTo(node.right.key) < 0) {
        node.right.left = splay(node.right.left, key);
        if (node.right.left != null) {
          node.right = rotateRight(node.right);
        }
      }
      return (node.right == null) ? node : rotateLeft(node);
    }
  }

  public void set(K key, V value) {
    if (isEmpty()) {
      root = new Node(key, value);
      return;
    }

    root = splay(root, key);

    if (root.key.equals(key)) {
      root.value = value; // Update value if key already exists
      return;
    }

    Node newNode = new Node(key, value);
    if (key.compareTo(root.key) < 0) {
      newNode.right = root;
      newNode.left = root.left;
      root.left = null;
    } else {
      newNode.left = root;
      newNode.right = root.right;
      root.right = null;
    }
    root = newNode;
  }

  public V get(K key) throws Exception {
    if (isEmpty()) {
      throw new Exception("Key " + key + " not found");
    }

    root = splay(root, key);

    if (root.key.equals(key)) {
      return root.value;
    } else {
      throw new Exception("Key " + key + " not found");
    }
  }

  public void del(K key) throws Exception {
    if (isEmpty()) {
      throw new Exception("Cannot delete from an empty tree.");
    }

    // Splay the tree to bring the node with the given key to the root
    root = splay(root, key);

    // If the root's key doesn't match the key to be deleted, the key is not in the
    // tree
    if (root == null || !root.key.equals(key)) {
      throw new Exception("Key " + key + " not found for deletion.");
    }

    // Handle deletion
    if (root.left == null) {
      // If there is no left subtree, promote the right subtree
      root = root.right;
    } else {
      // If there is a left subtree:
      // - Splay the maximum node in the left subtree to make it the root of the left
      // subtree
      Node temp = root.left;
      root.left = null; // Disconnect the left subtree
      temp = splay(temp, key); // Splay the maximum node in the left subtree

      // Attach the right subtree to the right of the new root
      temp.right = root.right;
      root = temp;
    }
  }
}
