class AVLNode:
    def __init__(self, key, val):
        self.key = key
        self.val = val
        self.left = None
        self.right = None
        self.height = 1  # Height of the node

class AVLTree:
    def __init__(self, root=None):
        self.root = root

    def is_empty(self):
        return self.root is None

    def _height(self, node):
        return node.height if node else 0

    def _get_balance(self, node):
        return self._height(node.left) - self._height(node.right) if node else 0

    def _rotate_left(self, z):
        y = z.right
        T2 = y.left
        y.left = z
        z.right = T2
        z.height = 1 + max(self._height(z.left), self._height(z.right))
        y.height = 1 + max(self._height(y.left), self._height(y.right))
        return y

    def _rotate_right(self, z):
        y = z.left
        T3 = y.right
        y.right = z
        z.left = T3
        z.height = 1 + max(self._height(z.left), self._height(z.right))
        y.height = 1 + max(self._height(y.left), self._height(y.right))
        return y

    def set(self, key, val):
        self.root = self._set_rec(self.root, key, val)

    def _set_rec(self, node, key, val):
        if node is None:
            return AVLNode(key, val)

        if key < node.key:
            node.left = self._set_rec(node.left, key, val)
        elif key > node.key:
            node.right = self._set_rec(node.right, key, val)
        else:
            node.val = val  # Update the value if key already exists
            return node

        node.height = 1 + max(self._height(node.left), self._height(node.right))

        balance = self._get_balance(node)

        # Left Left Case
        if balance > 1 and key < node.left.key:
            return self._rotate_right(node)

        # Right Right Case
        if balance < -1 and key > node.right.key:
            return self._rotate_left(node)

        # Left Right Case
        if balance > 1 and key > node.left.key:
            node.left = self._rotate_left(node.left)
            return self._rotate_right(node)

        # Right Left Case
        if balance < -1 and key < node.right.key:
            node.right = self._rotate_right(node.right)
            return self._rotate_left(node)

        return node

    def get(self, key):
        return self._get_rec(self.root, key)

    def _get_rec(self, node, key):
        if node is None:
            raise KeyError(key)
        if node.key == key:
            return node.val
        elif key < node.key:
            return self._get_rec(node.left, key)
        else:
            return self._get_rec(node.right, key)
