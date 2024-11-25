package uni.algos.hash_table;

import uni.algos.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.AbstractMap.SimpleEntry;

public class HashTable<K, V> extends AbstractMap<K, V> {
  private static class Node<K, V> extends SimpleEntry<K, V> {
    private Node<K, V> next;

    Node(K key, V value, Node<K, V> next) {
      super(key, value);
      this.next = next;
    }
  }

  private static final int INITIAL_CAPACITY = 16; // Starting size of the table
  private static final float LOAD_FACTOR = 0.75f; // Load factor for resizing

  private Node<K, V>[] table; // Array of nodes (buckets)
  private int size = 0; // Number of elements in the hash table

  // Constructor to initialize the hash table
  public HashTable() {
    table = new Node[INITIAL_CAPACITY];
  }

  // Put method to add or update a key-value pair
  @Override
  public V put(K key, V value) {
    int index = getIndex(key);

    // If no node exists at the index, create one
    if (table[index] == null) {
      table[index] = new Node<>(key, value, null);
    } else {
      Node<K, V> current = table[index];
      while (current.next != null) {
        if (current.getKey().equals(key)) {
          current.setValue(value); // Update value if key already exists
          return value;
        }
        current = current.next;
      }
      current.next = new Node<>(key, value, null); // Add to the end
    }

    size++;

    // Resize if load factor exceeded
    if (size > LOAD_FACTOR * table.length) {
      resize();
    }
    return value;
  }

  // Get method to retrieve the value associated with a key
  @Override
  public V get(Object key) {
    int index = getIndex(key);
    Node<K, V> current = table[index];

    while (current != null) {
      if (current.getKey().equals(key)) {
        return current.getValue();
      }
      current = current.next;
    }
    return null; // Key not found
  }

  // Remove method to delete a key-value pair
  @Override
  public V remove(Object key) {
    int index = getIndex(key);
    Node<K, V> current = table[index];
    Node<K, V> previous = null;

    while (current != null) {
      if (current.getKey().equals(key)) {
        if (previous == null) {
          table[index] = current.next; // Remove the head node
        } else {
          previous.next = current.next; // Remove the current node
        }
        size--;
        return previous.getValue();
      }
      previous = current;
      current = current.next;
    }
    return null;
  }

  protected int hash(Object object) {
    return object.hashCode();
  }

  protected int getIndex(Object key) {
    int hash = hash(key);
    return (hash & 0x7FFFFFFF) % table.length; // Ensure non-negative index
  }

  private void resize() {
    Node<K, V>[] oldTable = table;
    table = new Node[oldTable.length * 2];
    size = 0;

    for (Node<K, V> headNode : oldTable) {
      Node<K, V> current = headNode;
      while (current != null) {
        put(current.getKey(), current.getValue());
        current = current.next;
      }
    }
  }

  @Override
  public int size() {
    return size;
  }
}
