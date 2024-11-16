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
            self._set(self.root, key, val)
            
    def _set(self, node, key, val):
        while True:
            if key < node.key:
                if node.left is None:
                    node.left = Node(key, val)
                    break
                else:
                    node = node.left
            elif key > node.key:
                if node.right is None:
                    node.right = Node(key, val)
                    break
                else:
                    node = node.right
            else:
                node.val = val
                break

    def get(self, key):
        return self._get(self.root, key)

    def _get(self, node, key):
        while True:
            if node is None:
                raise KeyError(key)
            if node.key == key:
                return node.val
            elif key < node.key:
                node = node.left
            else:
                node = node.right

