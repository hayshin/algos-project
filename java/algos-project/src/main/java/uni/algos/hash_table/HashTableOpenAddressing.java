package uni.algos.hash_table;


import uni.algos.AbstractMap;
// import uni.algos.DataStructure;

public class HashTableOpenAddressing<K extends Comparable<K>, V> extends AbstractMap<K, V> {
  // Inner class for Node (used in separate chaining)

  private static final int INITIAL_CAPACITY = 16; // Starting size of the table
  private static final float LOAD_FACTOR = 0.75f; // Load factor for resizing

  private K[] keys;
  private V[] values;
  private int size = 0; // Number of elements in the hash table

  // Constructor to initialize the hash table
  public HashTableOpenAddressing() {
    keys = (K[]) new Comparable[INITIAL_CAPACITY];
    values = (V[]) new Object[INITIAL_CAPACITY];
  }

  // Put method to add or update a key-value pair
  @Override
  public V put(K key, V value) {
    int index = getIndex(key);
    int initialIndex = index;
    int i = 1;
    while (keys[index] != null && !keys[index].equals(key) && i < keys.length) {
      index = initialIndex + i * i;
      i++;
      index %= keys.length;
    }
    if (keys[index] == null) {
      keys[index] = key;
      values[index] = value;
      size++;
    } else {
      values[index] = value;
    }
    if (size > LOAD_FACTOR * keys.length) {
      resize();
    }
    return null;
  }

  // Get method to retrieve the value associated with a key
  @Override
  public V get(Object key) {
    int index = getIndex(key);
    int initialIndex = index;
    int i = 1;

    while (keys[index] != null && i < keys.length) {
      if (keys[index].equals(key)) {
        return values[index];
      }
      index = initialIndex + i * i;
      i++;
      index %= keys.length;
    }

    return null; // Key not found
  }

  // Remove method to delete a key-value pair
  @Override
  public V remove(Object key) {
    int index = getIndex(key);
    int i = 0;
    int initialIndex = index;

    while (keys[index] != null && i < keys.length) {
      if (keys[index].equals(key)) {
        keys[index] = null;
        size--;
        return values[index];
      }
      index = initialIndex + i * i;
      i++;
      index %= keys.length;
    }
    return null;
  }

  // Helper method to compute the index in the table
  private int getIndex(Object key) {
    int hash = key.hashCode();
    return (hash & 0x7FFFFFFF) % keys.length; // Ensure non-negative index
  }

  // Resize the table when load factor exceeded
  private void resize() {
    K[] oldKeys = keys;
    V[] oldValues = values;
    keys = (K[]) new Comparable[oldKeys.length * 2];
    values = (V[]) new Object[oldKeys.length * 2];
    size = 0;

    for (int i = 0; i < oldKeys.length; i++) {
      if (oldKeys[i] != null)
        put(oldKeys[i], oldValues[i]); // Rehash and add elements to new table
    }
  }

  @Override
  public int size() {
    return size;
  }
}
