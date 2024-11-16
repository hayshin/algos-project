class SplayNode:
    def __init__(self, key, val):
        self.key = key
        self.val = val
        self.left = None
        self.right = None

class SplayTree:
    def __init__(self, root=None):
        self.root = root

    def is_empty(self):
        return self.root is None

    def _rotate_left(self, x):
        y = x.right
        x.right = y.left
        y.left = x
        return y

    def _rotate_right(self, x):
        y = x.left
        x.left = y.right
        y.right = x
        return y

    def _splay(self, node, key):
        if node is None or node.key == key:
            return node

        # Key lies in the left subtree
        if key < node.key:
            if node.left is None:
                return node

            # Zig-Zig (Left Left)
            if key < node.left.key:
                node.left.left = self._splay(node.left.left, key)
                node = self._rotate_right(node)

            # Zig-Zag (Left Right)
            elif key > node.left.key:
                node.left.right = self._splay(node.left.right, key)
                if node.left.right:
                    node.left = self._rotate_left(node.left)

            return self._rotate_right(node) if node.left else node

        # Key lies in the right subtree
        else:
            if node.right is None:
                return node

            # Zag-Zag (Right Right)
            if key > node.right.key:
                node.right.right = self._splay(node.right.right, key)
                node = self._rotate_left(node)

            # Zag-Zig (Right Left)
            elif key < node.right.key:
                node.right.left = self._splay(node.right.left, key)
                if node.right.left:
                    node.right = self._rotate_right(node.right)

            return self._rotate_left(node) if node.right else node

    def set(self, key, val):
        if self.is_empty():
            self.root = SplayNode(key, val)
            return

        self.root = self._splay(self.root, key)

        if self.root.key == key:
            self.root.val = val  # Update value if key already exists
            return

        new_node = SplayNode(key, val)
        if key < self.root.key:
            new_node.right = self.root
            new_node.left = self.root.left
            self.root.left = None
        else:
            new_node.left = self.root
            new_node.right = self.root.right
            self.root.right = None

        self.root = new_node

    def get(self, key):
        if self.is_empty():
            raise KeyError(f"Key {key} not found")

        self.root = self._splay(self.root, key)
        if self.root.key == key:
            return self.root.val
        else:
            raise KeyError(f"Key {key} not found")
