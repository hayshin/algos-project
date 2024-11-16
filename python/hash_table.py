class Node:
    def __init__(self, key, val):
        self.key = key
        self.val = val
        self.next = None


class HashTable:
    def __init__(self, capacity=16):
        self.capacity = capacity
        self.size = 0
        self.table = [None] * capacity

    def index(self, key):
        return hash(key) % self.capacity

    def set(self, key, val):
        index = self.index(key)
        if self.table[index] is None:
            self.table[index] = Node(key, val)
            self.size += 1
        else:
            current = self.table[index]
            while current:
                if current.key == key:
                    return
                current = current.next
            new_node = Node(key, val)
            new_node.next = self.table[index]
            self.table[index] = new_node
            self.size += 1

    def get(self, key):
        index = self.index(key)
        current = self.table[index]
        while current:
            if current.key == key:
                return current.val
            current = current.next
        raise KeyError(key) 

    def remove(self, key):
        index = self.index(key)
        previous = None
        current = self.table[index]

        while current:
            if current.key == key:
                if previous:
                    previous.next = current.next
                else:
                    self.table[index] = current.next
                self.size -= 1
                return       
            previous = current
            current = current.next
        raise KeyError(key)

    def __str__(self):
        elements = []
        for i in range(self.capacity):
            current = self.table[i]
            while current:
                elements.append((current.key, current.val))
                current = current.next
        return str(elements)
        
    def __len__(self):
        return self.size

    def __contains__(self, key):
        try:
            self.get(key)
            return True
        except KeyError:
            return False
