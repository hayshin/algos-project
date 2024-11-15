class Node:
    def __init__(self, key, val):
        self.key = key
        self.val = val
        self.left = None
        self.right = None

class BinarySearchTree:
    def __init__(self, root=None):
        self.root = root

    def is_empty(self):
        return self.root is None

    def set(self, key, val):
        if self.is_empty():
            self.root = Node(key, val)
        else:
            self._set_rec(self.root, key)
            
    def _set_rec(self, node, key):
        if key < node.key:
            if node.left is None:
                node.left = Node(key, val)
            else:
                self._set_rec(node.left, key)
        elif key > node.key:
            if node.right is None:
                node.right = Node(key, val)
            else:
                self._set_rec(node.right, key)

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

