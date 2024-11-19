package uni.algos.trees;

import uni.algos.DataStructure;

public class AVLTreeArr<K extends Comparable<K>, V> implements DataStructure<K, V> {

  K[] keys;
  V[] values;
  int[] heights;
  private int left(int index) {
    return index * 2 + 1;
  }
  private int right(int index){
    return index * 2 + 2;
  }
  private int parent(int index) {
    return index / 2 - 1;
  }
  private class Node {
    K key;
    V value;
    Node left, right;
    int height;

    Node(K key, V value) {
      this.key = key;
      this.value = value;
      height = 1; // New node is initially added at leaf
    }
  }

  private Node root;

  // Method to get the value associated with a key
  public V get(K key) {
    return get(0, key);
  }

  private V get(int index, K key) {
    while (values[index] != null) {
      int cmp = key.compareTo(keys[index]);
      if (cmp == 0)
        return values[index];
      else if (cmp < 0)
        index = left(index);
      else
        index = right(index);
    }
    return null;
  }

  // Method to insert key-value pair into the tree
  public void set(K key, V value) {
    root = insert(root, key, value);
  }

  private Node insert(Node node, K key, V value) {
    if (node == null)
      return new Node(key, value);

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

  // Method to delete a key from the tree
  public void del(K key) {
    root = delete(root, key);
  }

  private Node delete(Node node, K key) {
    if (node == null)
      return null;

    int cmp = key.compareTo(node.key);
    if (cmp < 0)
      node.left = delete(node.left, key);
    else if (cmp > 0)
      node.right = delete(node.right, key);
    else {
      // Node with only one child or no child
      if ((node.left == null) || (node.right == null)) {
        Node temp = null;
        if (temp == node.left)
          temp = node.right;
        else
          temp = node.left;

        // No child case
        if (temp == null) {
          temp = node;
          node = null;
        } else
          node = temp; // One child case
      } else {
        // Node with two children: Get the inorder successor (smallest in the right
        // subtree)
        Node temp = minValueNode(node.right);

        // Copy the inorder successor's data to this node
        node.key = temp.key;
        node.value = temp.value;

        // Delete the inorder successor
        node.right = delete(node.right, temp.key);
      }
    }

    // If the tree had only one node then return
    if (node == null)
      return null;

    updateHeight(node);
    return rebalance(node);
  }

  // Helper function to update the height of the node
  private void updateHeight(int index) {
    heights[index] = 1 + Math.max(height(left(index)), height(right(index)));
  }

  // Helper function to get the balance factor of node
  private int getBalance(int index) {
    return keys[index] == null  ? 0 : height(left(index)) - height(right(index));
  }

  // Rebalance the node if necessary
  private int rebalance(int index) {
    int balance = getBalance(index);

    // Left Left Case
    if (balance > 1 && getBalance(left(index)) >= 0)
      return rightRotate(node);

    // Left Right Case
    if (balance > 1 && getBalance(node.left) < 0) {
      node.left = leftRotate(node.left);
      return rightRotate(index);
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

  private void changePosition(int prevIndex, int newIndex) {
    keys[newIndex] = keys[prevIndex];
    values[newIndex] = values[prevIndex];
    heights[newIndex] = heights[prevIndex];
    
  }

  // Right rotate subtree rooted with y
  private int rightRotate(int y) {
    int x = left(y);
    int T2 = right(x);

    // Perform rotation
    changePosition(y, right(x));
    changePosition(T2, left(y));

    // Update heights
    updateHeight(y);
    updateHeight(x);

    // Return new root
    return x;
  }
  private int leftRotate(int x) {
    int y = right(x);
    int T2 = left(y);

    // Perform rotation
    changePosition(x,left(y));
    changePosition(T2, right(x));

    // Update heights
    updateHeight(y);
    updateHeight(x);

    // Return new root
    return x;
  }

  // Left rotate subtree rooted with x
  // Get the height of the node
  private int height(int index) {
    return keys[index] == null ? 0 : heights[index];
  }

  // Get the node with minimum key value found in that tree
  private Node minValueNode(Node node) {
    Node current = node;

    // Loop down to find the leftmost leaf
    while (current.left != null)
      current = current.left;

    return current;
  }
}
