package uni.algos.trees;

class SplayNode<K extends Comparable<K>, V> {
    K key;
    V value;
    SplayNode<K, V> left, right;

    public SplayNode(K key, V value) {
        this.key = key;
        this.value = value;
        this.left = null;
        this.right = null;
    }
}

public class SplayTree<K extends Comparable<K>, V> {
    private SplayNode<K, V> root;

    public SplayTree() {
        this.root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    private SplayNode<K, V> rotateLeft(SplayNode<K, V> x) {
        SplayNode<K, V> y = x.right;
        x.right = y.left;
        y.left = x;
        return y;
    }

    private SplayNode<K, V> rotateRight(SplayNode<K, V> x) {
        SplayNode<K, V> y = x.left;
        x.left = y.right;
        y.right = x;
        return y;
    }

    private SplayNode<K, V> splay(SplayNode<K, V> node, K key) {
        if (node == null || node.key.equals(key)) {
            return node;
        }

        // Key lies in the left subtree
        if (key.compareTo(node.key) < 0) {
            if (node.left == null) return node;

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
            if (node.right == null) return node;

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
            root = new SplayNode<>(key, value);
            return;
        }

        root = splay(root, key);

        if (root.key.equals(key)) {
            root.value = value; // Update value if key already exists
            return;
        }

        SplayNode<K, V> newNode = new SplayNode<>(key, value);
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
}
