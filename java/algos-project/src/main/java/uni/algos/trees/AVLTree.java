package uni.algos.trees;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class AVLTree<K extends Comparable<K>, V> extends TreeMap<K, V, AVLTree<K, V>.Node> {
  class Node extends TreeEntry<K, V, Node> {
    int height;

    Node(K key, V value) {
      super(key, value);
      height = 1; // New node is initially added at leaf
    }
  }

  public Node createNode(K key, V value) {
    return new Node(key, value);
  }

  private int height(Node node) {
    return node == null ? 0 : node.height;
  }

  private void updateHeight(Node node) {
    node.height = 1 + Math.max(height(node.left), height(node.right));
  }

  // Helper function to get the balance factor of node
  private int getBalance(Node node) {
    return (node == null) ? 0 : height(node.left) - height(node.right);
  }

  // Rebalance the node if necessary
  private Node rebalance(Node node) {
    int balance = getBalance(node);

    // Left Left Case
    if (balance > 1 && getBalance(node.left) >= 0)
      return rightRotate(node);

    // Left Right Case
    if (balance > 1 && getBalance(node.left) < 0) {
      node.left = leftRotate(node.left);
      return rightRotate(node);
    }

    // Right Right Case
    if (balance < -1 && getBalance(node.right) <= 0)
      return leftRotate(node);

    // Right Left Case
    if (balance < -1 && getBalance(node.right) > 0) {
      node.right = rightRotate(node.right);
      return leftRotate(node);
    }

    return node;
  }

  // Right rotate subtree rooted with y
  private Node rightRotate(Node y) {
    Node x = y.left;
    Node T2 = x.right;

    // Perform rotation
    x.right = y;
    y.left = T2;

    // Update heights
    updateHeight(y);
    updateHeight(x);

    // Return new root
    return x;
  }

  // Left rotate subtree rooted with x
  private Node leftRotate(Node x) {
    Node y = x.right;
    Node T2 = y.left;

    // Perform rotation
    y.left = x;
    x.right = T2;

    // Update heights
    updateHeight(x);
    updateHeight(y);

    // Return new root
    return y;
  }

  // Get the height of the node

  // Method to insert key-value pair into the tree
  @Override
  public V put(K key, V value) {
    root = insert(root, key, value);
    return value;
  }

  private Node insert(Node node, K key, V value) {
    if (node == null) return new Node(key, value);

    int cmp = key.compareTo(node.key);
    if (cmp < 0)
      node.left = insert(node.left, key, value);
    else if (cmp > 0)
      node.right = insert(node.right, key, value);
    else {
      node.value = value; // Update the value if key already exists
      return node;
    }

    updateHeight(node);
    return rebalance(node);
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
