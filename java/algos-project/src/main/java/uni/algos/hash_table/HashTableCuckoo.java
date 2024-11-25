package uni.algos.hash_table;

import uni.algos.AbstractMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Random;

/**
 * Cuckoo hashing allows closer to perfect hashing by using 2 different
 * hashcodes
 * that allow 2 positions in the table for each key-value pair
 * 
 * @author Cam
 * @version 1.0, 7/20/2012
 */
public class HashTableCuckoo<K, V> extends AbstractMap<K, V> {

  private int CAPACITY;
  private int a = 37, b = 17;
  private Node[] table;

  private class Node {
    private K key = null;
    private V value = null;

    public Node(K k, V v) {
      key = k;
      value = v;
    }

    private K getKey() {
      return key;
    }

    private V getValue() {
      return value;
    }
    // private void setValue(V value) {
    // this.value = value;
    // }
  }
  public HashTableCuckoo() {
    int size = 16;
    CAPACITY = size;
    table = (Node[]) new Object[CAPACITY];
  }

  public HashTableCuckoo(int size) {
    CAPACITY = size;
    table = (Node[]) new Object[CAPACITY];
  }

  public int size() {
    int count = 0;
    for (int i = 0; i < CAPACITY; ++i) {
      if (table[i] != null)
        count++;
    }
    return count;
  }

  public V put(K key, V value) {
    int index = key.hashCode() % CAPACITY;
    int index2 = altHashCode(key) % CAPACITY;

    if (table[index] != null && table[index].getValue().equals(value))
      return null;
    if (table[index2] != null && table[index2].getValue().equals(value))
      return null;
    int pos = index;
    Node buck = new Node(key, value);
    for (int i = 0; i < 3; i++) {
      if (table[pos] == null) {
        table[pos] = buck;
        return null;
      } else {
        Node copy = table[pos];
        table[pos] = buck;
        buck = copy;
      }
      if (pos == index)
        pos = index2;
      else
        pos = index;
    }
    rehash();
    return put(key, value);
  }

  @Override
  public V get(Object key) {
    int index = key.hashCode() % CAPACITY;
    int index2 = altHashCode(key) % CAPACITY;
    if (table[index] != null && table[index].getKey().equals(key))
      return table[index].getValue();
    else if (table[index2] != null && table[index2].getKey().equals(key))
      return table[index2].getValue();
    return null;
  }

  public int altHashCode(Object key) {
    return a * b + key.hashCode();
  }

  @Override
  public V remove(Object key) {
    int index = key.hashCode() % CAPACITY;
    int index2 = altHashCode(key) % CAPACITY;
    if (table[index] != null) {
      table[index] = null;
      return null;
    } else if (table[index2] != null) {
      table[index2] = null;
      return null;
    }
    return null;
  }

  public void rehash() {
    Node[] tableCopy = table.clone();
    int OLD_CAPACITY = CAPACITY;
    CAPACITY = (CAPACITY * 2) + 1;
    table = (Node[]) new Object[CAPACITY];

    for (int i = 0; i < OLD_CAPACITY; ++i) {
      if (tableCopy[i] != null) {
        put(tableCopy[i].getKey(), tableCopy[i].getValue());
      }
    }
    // reset alt hash func
    Random gen = new Random();
    a = gen.nextInt(37);
    b = gen.nextInt(17);
  }

}
