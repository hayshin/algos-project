class RedBlackNode:
    def __init__(self, key, val, color='RED'):
        self.key = key
        self.val = val
        self.color = color  # 'RED' or 'BLACK'
        self.left = None
        self.right = None
        self.parent = None

class RedBlackTree:
    def __init__(self, root=None):
        self.root = root
        self.TNULL = RedBlackNode(key=None, val=None, color='BLACK')  # Sentinel node
        self.root = self.TNULL

    def is_empty(self):
        return self.root == self.TNULL

    def set(self, key, val):
        node = RedBlackNode(key, val)
        node.left = node.right = node.parent = self.TNULL
        if self.is_empty():
            self.root = node
            self.root.color = 'BLACK'  # Root is always black
        else:
            self._set_rec(self.root, node)
        self._fix_insert(node)

    def _set_rec(self, current, node):
        if node.key < current.key:
            if current.left == self.TNULL:
                current.left = node
                node.parent = current
            else:
                self._set_rec(current.left, node)
        elif node.key > current.key:
            if current.right == self.TNULL:
                current.right = node
                node.parent = current
            else:
                self._set_rec(current.right, node)
        else:
            current.val = node.val  # Update the value if the key already exists

    def _rotate_left(self, x):
        y = x.right
        x.right = y.left
        if y.left != self.TNULL:
            y.left.parent = x
        y.parent = x.parent
        if x.parent is None:
            self.root = y
        elif x == x.parent.left:
            x.parent.left = y
        else:
            x.parent.right = y
        y.left = x
        x.parent = y

    def _rotate_right(self, x):
        y = x.left
        x.left = y.right
        if y.right != self.TNULL:
            y.right.parent = x
        y.parent = x.parent
        if x.parent is None:
            self.root = y
        elif x == x.parent.right:
            x.parent.right = y
        else:
            x.parent.left = y
        y.right = x
        x.parent = y

    def _fix_insert(self, node):
        while node != self.root and node.parent.color == 'RED':
            if node.parent == node.parent.parent.left:
                uncle = node.parent.parent.right
                if uncle.color == 'RED':  # Case 1: Uncle is red
                    node.parent.color = 'BLACK'
                    uncle.color = 'BLACK'
                    node.parent.parent.color = 'RED'
                    node = node.parent.parent
                else:
                    if node == node.parent.right:  # Case 2: Node is right child
                        node = node.parent
                        self._rotate_left(node)
                    # Case 3: Node is left child
                    node.parent.color = 'BLACK'
                    node.parent.parent.color = 'RED'
                    self._rotate_right(node.parent.parent)
            else:
                uncle = node.parent.parent.left
                if uncle.color == 'RED':  # Case 1: Uncle is red
                    node.parent.color = 'BLACK'
                    uncle.color = 'BLACK'
                    node.parent.parent.color = 'RED'
                    node = node.parent.parent
                else:
                    if node == node.parent.left:  # Case 2: Node is left child
                        node = node.parent
                        self._rotate_right(node)
                    # Case 3: Node is right child
                    node.parent.color = 'BLACK'
                    node.parent.parent.color = 'RED'
                    self._rotate_left(node.parent.parent)
        self.root.color = 'BLACK'

    def get(self, key):
        return self._get_rec(self.root, key)

    def _get_rec(self, node, key):
        if node == self.TNULL:
            raise KeyError(key)
        if node.key == key:
            return node.val
        elif key < node.key:
            return self._get_rec(node.left, key)
        else:
            return self._get_rec(node.right, key)
