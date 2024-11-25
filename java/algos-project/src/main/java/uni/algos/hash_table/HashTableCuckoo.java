package uni.algos.hash_table;

import java.util.Arrays;
import uni.algos.AbstractMap;

public class HashTableCuckoo<K, V> extends AbstractMap<K, V> {
  private static final int INITIAL_CAPACITY = 16;
  private static final int MAX_REHASH_ATTEMPTS = 32;

  private K[] table1Keys;
  private V[] table1Values;
  private K[] table2Keys;
  private V[] table2Values;
  private int size;

  // Constructor to initialize hash tables
  public HashTableCuckoo() {
        table1Keys = (K[]) new Object[INITIAL_CAPACITY];
        table1Values = (V[]) new Object[INITIAL_CAPACITY];
        table2Keys = (K[]) new Object[INITIAL_CAPACITY];
        table2Values = (V[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

  // Put method to add or update a key-value pair
  @Override
  public V put(K key, V value) {
    if (containsKey(key)) {
      int index = hash1(key);
      if (table1Keys[index] != null) {
        table1Values[index] = value;
      } else {
        index = hash2(key);
        table2Values[index] = value;
      }
      return null;
    }

    // Insert the key-value pair
    K currentKey = key;
    V currentValue = value;

    for (int attempt = 0; attempt < MAX_REHASH_ATTEMPTS; attempt++) {
      // Attempt to insert in Table 1
      int index1 = hash1(currentKey);
      if (table1Keys[index1] == null) {
        table1Keys[index1] = currentKey;
        table1Values[index1] = currentValue;
        size++;
        return null;
      }

      // Evict existing key-value pair from Table 1
      K evictedKey = table1Keys[index1];
      V evictedValue = table1Values[index1];
      table1Keys[index1] = currentKey;
      table1Values[index1] = currentValue;

      // Attempt to insert evicted pair into Table 2
      currentKey = evictedKey;
      currentValue = evictedValue;
      int index2 = hash2(currentKey);
      if (table2Keys[index2] == null) {
        table2Keys[index2] = currentKey;
        table2Values[index2] = currentValue;
        size++;
        return null;
      }

      // Evict existing key-value pair from Table 2
      evictedKey = table2Keys[index2];
      evictedValue = table2Values[index2];
      table2Keys[index2] = currentKey;
      table2Values[index2] = currentValue;

      currentKey = evictedKey;
      currentValue = evictedValue;
    }

    // Rehash if MAX_REHASH_ATTEMPTS is reached
    rehash();
    return put(currentKey, currentValue);
  }

  // Get method to retrieve the value associated with a key
  public V get(Object key) {
    int index1 = hash1(key);
    if (table1Keys[index1] != null && table1Keys[index1].equals(key)) {
      return table1Values[index1];
    }

    int index2 = hash2(key);
    if (table2Keys[index2] != null && table2Keys[index2].equals(key)) {
      return table2Values[index2];
    }

    return null;
  }

  // Remove method to delete a key-value pair
  public V remove(Object key) {
    int index1 = hash1(key);
    if (table1Keys[index1] != null && table1Keys[index1].equals(key)) {
      V removedValue = table1Values[index1];
      table1Keys[index1] = null;
      table1Values[index1] = null;
      size--;
      return removedValue;
    }

    int index2 = hash2(key);
    if (table2Keys[index2] != null && table2Keys[index2].equals(key)) {
      V removedValue = table2Values[index2];
      table2Keys[index2] = null;
      table2Values[index2] = null;
      size--;
      return removedValue;
    }

    return null; // Key not found
  }

  private void rehash() {
    K[] oldTable1Keys = table1Keys;
    V[] oldTable1Values = table1Values;
    K[] oldTable2Keys = table2Keys;
    V[] oldTable2Values = table2Values;

    int newCapacity = table1Keys.length * 2;
    table1Keys = (K[]) new Object[newCapacity];
    table1Values = (V[]) new Object[newCapacity];
    table2Keys = (K[]) new Object[newCapacity];
    table2Values = (V[]) new Object[newCapacity];
    size = 0;

    for (int i = 0; i < oldTable1Keys.length; i++) {
      if (oldTable1Keys[i] != null) {
        put(oldTable1Keys[i], oldTable1Values[i]);
      }
    }
    for (int i = 0; i < oldTable2Keys.length; i++) {
      if (oldTable2Keys[i] != null) {
        put(oldTable2Keys[i], oldTable2Values[i]);
      }
    }
  }

  // Hash function 1
  private int hash1(Object key) {
    return Math.abs(key.hashCode()) % table1Keys.length;
  }

  // Hash function 2
  private int hash2(Object key) {
    return (Math.abs(key.hashCode() / 7) % table2Keys.length);
  }

  // Get the current size of the hash table
  public int size() {
    return size;
  }

}
