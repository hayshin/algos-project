package uni.algos;

import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;

public abstract class AbstractMap<K, V>  implements Map<K, V> {

  protected K toKey(Object k) {
    @SuppressWarnings("unchecked")
    K key = (K) k;
    return key;
  }
  protected V toValue(Object v) {
    @SuppressWarnings("unchecked")
    V value = (V) v;
    return value;
  }

  @Override
  public abstract V get(Object key);

  @Override
  public abstract V put(K key, V value); 

  @Override
  public void putAll(Map<? extends K, ? extends V> map) {
    for (Entry<? extends K, ? extends V> entry : map.entrySet()) {
      put(entry.getKey(), entry.getValue());
    }
  }

  @Override
  public V remove(Object k) {
    throw new UnsupportedOperationException();
  } 

  @Override
  public abstract int size(); 

  @Override
  public boolean containsKey(Object k) {
    return get(k) != null;
  }

  @Override
  public boolean containsValue(Object v) {
    throw new UnsupportedOperationException();
  } 

  @Override
  public Set<Entry<K, V>> entrySet() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Set<K> keySet() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Collection<V> values() {
    throw new UnsupportedOperationException();
  }

  // private void traverseValues(E node, ArrayList<V> values) {
  //   if (node == null)
  //     return;
  //   traverseValues(node.left, values);
  //   values.add(node.value);
  //   traverseValues(node.right, values);
  // }

  // public Object[] toArray() {
  //   Object[] array = new Object[size()];
  //   toArray(root, array, 0);
  //   return array;
  // }

  // public <T> T[] toArray(T[] array) {
  //   toArray(root, array, 0);
  //   return array;
  // }

  // private int toArray(E node, Object[] array, int index) {
  //   if (node == null)
  //     return index;
  //   index = toArray(node.left, array, index);
  //   array[index] = node.key;
  //   index = toArray(node.right, array, index);
  //   return index + 1;
  // }

  @Override
  public boolean isEmpty() {
    return size() == 0;
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  } 
}
