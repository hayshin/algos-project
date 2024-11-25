package uni.algos.trees;

import java.util.AbstractMap;
import java.util.Set;

public class RedBlackTree<K extends Comparable<K>, V> extends TreeMap<K, V, RedBlackTree<K, V>.Node> {

  protected Node createNode(K key, V value) {
    return new Node(key, value);
  }

  private static final boolean RED = true;
  private static final boolean BLACK = false;

  class Node extends TreeEntry<K, V, Node> {
    Node parent;
    boolean color;

    Node(K key, V value) {
      super(key, value);
      this.color = RED;
    }

    Node(K key, V value, boolean color) {
      super(key, value);
      this.color = color;
    }

    Node(K key, V value, boolean color, Node parent) {
      super(key, value);
      this.color = color;
      this.parent = parent;
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

  // Method to get the value associated with a key

  // Method to insert key-value pair into the tree
  @Override
  public V put(K key, V value) {
    Node insertedNode = insert(root, key, value);
    if (insertedNode != null) {
      fixAfterInsertion(insertedNode);
    }
    return value;
  }

  private Node insert(Node h, K key, V value) {
    if (root == null) {
      root = new Node(key, value, BLACK, null);
      return root;
    }

    Node parent = null;
    Node current = root;
    int cmp = 0;
    while (current != null) {
      parent = current;
      cmp = key.compareTo(current.getKey());
      if (cmp == 0) {
        current.setValue(value);
        return null;
      } else if (cmp < 0) {
        current = current.left;
      } else {
        current = current.right;
      }
    }

    Node newNode = new Node(key, value, RED, parent);
    if (cmp < 0) {
      parent.left = newNode;
    } else {
      parent.right = newNode;
    }

    return newNode;
  }

  private void fixAfterInsertion(Node x) {
    x.color = RED;

    while (x != null && x != root && x.parent.color == RED) {
      if (parentOf(x) == leftOf(parentOf(parentOf(x)))) {
        Node y = rightOf(parentOf(parentOf(x)));
        if (colorOf(y) == RED) {
          setColor(parentOf(x), BLACK);
          setColor(y, BLACK);
          setColor(parentOf(parentOf(x)), RED);
          x = parentOf(parentOf(x));
        } else {
          if (x == rightOf(parentOf(x))) {
            x = parentOf(x);
            leftRotate(x);
          }
          setColor(parentOf(x), BLACK);
          setColor(parentOf(parentOf(x)), RED);
          rightRotate(parentOf(parentOf(x)));
        }
      } else {
        Node y = leftOf(parentOf(parentOf(x)));
        if (colorOf(y) == RED) {
          setColor(parentOf(x), BLACK);
          setColor(y, BLACK);
          setColor(parentOf(parentOf(x)), RED);
          x = parentOf(parentOf(x));
        } else {
          if (x == leftOf(parentOf(x))) {
            x = parentOf(x);
            rightRotate(x);
          }
          setColor(parentOf(x), BLACK);
          setColor(parentOf(parentOf(x)), RED);
          leftRotate(parentOf(parentOf(x)));
        }
      }
    }
    root.color = BLACK;
  }

  // Method to delete a key from the tree
  @Override
  public V remove(Object k) {

    if (!(k instanceof Comparable))
      throw new ClassCastException();
    @SuppressWarnings("unchecked")
    K key = (K) k;
    Node node = get(root, key);
    if (node == null)
      return null;

    deleteNode(node);
    return value(node);
  }

  private void deleteNode(Node z) {
    Node y = z;
    Node x;
    boolean yOriginalColor = y.color;

    if (z.left == null) {
      x = z.right;
      transplant(z, z.right);
    } else if (z.right == null) {
      x = z.left;
      transplant(z, z.left);
    } else {
      y = min(z.right);
      yOriginalColor = y.color;
      x = y.right;
      if (y.parent == z) {
        if (x != null)
          x.parent = y;
      } else {
        transplant(y, y.right);
        y.right = z.right;
        if (y.right != null)
          y.right.parent = y;
      }
      transplant(z, y);
      y.left = z.left;
      if (y.left != null)
        y.left.parent = y;
      y.color = z.color;
    }
    if (yOriginalColor == BLACK) {
      fixAfterDeletion(x);
    }
  }

  private void fixAfterDeletion(Node x) {
    while (x != root && colorOf(x) == BLACK) {
      if (x == leftOf(parentOf(x))) {
        Node w = rightOf(parentOf(x));

        if (colorOf(w) == RED) {
          setColor(w, BLACK);
          setColor(parentOf(x), RED);
          leftRotate(parentOf(x));
          w = rightOf(parentOf(x));
        }

        if (colorOf(leftOf(w)) == BLACK && colorOf(rightOf(w)) == BLACK) {
          setColor(w, RED);
          x = parentOf(x);
        } else {
          if (colorOf(rightOf(w)) == BLACK) {
            setColor(leftOf(w), BLACK);
            setColor(w, RED);
            rightRotate(w);
            w = rightOf(parentOf(x));
          }
          setColor(w, colorOf(parentOf(x)));
          setColor(parentOf(x), BLACK);
          setColor(rightOf(w), BLACK);
          leftRotate(parentOf(x));
          x = root;
        }
      } else {
        Node w = leftOf(parentOf(x));

        if (colorOf(w) == RED) {
          setColor(w, BLACK);
          setColor(parentOf(x), RED);
          rightRotate(parentOf(x));
          w = leftOf(parentOf(x));
        }

        if (colorOf(rightOf(w)) == BLACK && colorOf(leftOf(w)) == BLACK) {
          setColor(w, RED);
          x = parentOf(x);
        } else {
          if (colorOf(leftOf(w)) == BLACK) {
            setColor(rightOf(w), BLACK);
            setColor(w, RED);
            leftRotate(w);
            w = leftOf(parentOf(x));
          }
          setColor(w, colorOf(parentOf(x)));
          setColor(parentOf(x), BLACK);
          setColor(leftOf(w), BLACK);
          rightRotate(parentOf(x));
          x = root;
        }
      }
    }
    if (x != null)
      x.color = BLACK;
  }

  private void leftRotate(Node x) {
    if (x == null)
      return;
    Node y = x.right;
    x.right = y.left;

    if (y.left != null)
      y.left.parent = x;

    y.parent = x.parent;

    if (x.parent == null)
      root = y;
    else if (x == x.parent.left)
      x.parent.left = y;
    else
      x.parent.right = y;

    y.left = x;
    x.parent = y;
  }

  private void rightRotate(Node x) {
    if (x == null)
      return;
    Node y = x.left;
    x.left = y.right;

    if (y.right != null)
      y.right.parent = x;

    y.parent = x.parent;

    if (x.parent == null)
      root = y;
    else if (x == x.parent.right)
      x.parent.right = y;
    else
      x.parent.left = y;

    y.right = x;
    x.parent = y;
  }

  private void transplant(Node u, Node v) {
    if (u.parent == null)
      root = v;
    else if (u == u.parent.left)
      u.parent.left = v;
    else
      u.parent.right = v;

    if (v != null)
      v.parent = u.parent;
  }


  // Helper methods to get parent, left, right, and color of a node
  private Node parentOf(Node node) {
    return node == null ? null : node.parent;
  }

  private boolean colorOf(Node node) {
    return node == null ? BLACK : node.color;
  }

  private void setColor(Node node, boolean color) {
    if (node != null)
      node.color = color;
  }
}
