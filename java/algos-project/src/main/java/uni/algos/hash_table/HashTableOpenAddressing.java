package uni.algos.hash_table;

import uni.algos.AbstractMap;

public class HashTableOpenAddressing<K, V> extends AbstractMap<K, V> {

    private static final int INITIAL_CAPACITY = 16; // Starting size of the table
    private static final float LOAD_FACTOR = 0.75f; // Load factor for resizing

    private K[] keys; // Array to store keys
    private V[] values; // Array to store values
    private int size = 0; // Number of elements in the hash table

    // Constructor to initialize the hash table
    public HashTableOpenAddressing() {
        keys = (K[]) new Object[INITIAL_CAPACITY];
        values = (V[]) new Object[INITIAL_CAPACITY];
    }

    // Put method to add or update a key-value pair
    @Override
    public V put(K key, V value) {
        int index = getIndex(key);
        int i = 0;

        // Use quadratic probing to find an available spot
        while (keys[index] != null) {
            if (keys[index].equals(key)) { // If key already exists, update the value
                V oldValue = values[index];
                values[index] = value;
                return oldValue;
            }
            i++;
            index = (index + i * i) % keys.length; // Move to the next quadratic index
        }

        // Add the new key-value pair
        keys[index] = key;
        values[index] = value;
        size++;

        // Check if resizing is needed
        if (size > LOAD_FACTOR * keys.length) {
            resize();
        }

        return null;
    }

    // Get method to retrieve the value associated with a key
    @Override
    public V get(Object key) {
        int index = getIndex(key);
        int i = 0;

        // Use quadratic probing to search for the key
        while (keys[index] != null) {
            if (keys[index].equals(key)) {
                return values[index]; // Return value if key is found
            }
            i++;
            index = (index + i * i) % keys.length; // Move to the next quadratic index
        }

        return null; // Key not found
    }

    // Remove method to delete a key-value pair
    @Override
    public V remove(Object key) {
        int index = getIndex(key);
        int i = 0;

        // Use quadratic probing to find the key
        while (keys[index] != null) {
            if (keys[index].equals(key)) {
                V oldValue = values[index];
                keys[index] = null; // Mark the key slot as null
                values[index] = null;
                size--;

                // Rehash all keys in the same cluster to prevent breaking the chain
                rehashCluster(index);

                return oldValue;
            }
            i++;
            index = (index + i * i) % keys.length; // Move to the next quadratic index
        }

        return null; // Key not found
    }

    // Helper method to rehash a cluster after a key is removed
    private void rehashCluster(int start) {
        int i = 1;
        int index = (start + i * i) % keys.length;

        while (keys[index] != null) {
            K rehashKey = keys[index];
            V rehashValue = values[index];

            keys[index] = null;
            values[index] = null;
            size--;

            put(rehashKey, rehashValue); // Re-insert the key-value pair
            i++;
            index = (start + i * i) % keys.length;
        }
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

        keys = (K[]) new Object[oldKeys.length * 2];
        values = (V[]) new Object[oldKeys.length * 2];
        size = 0;

        // Rehash all existing keys and values into the new table
        for (int i = 0; i < oldKeys.length; i++) {
            if (oldKeys[i] != null) {
                put(oldKeys[i], oldValues[i]);
            }
        }
    }

    @Override
    public int size() {
        return size;
    }
}
