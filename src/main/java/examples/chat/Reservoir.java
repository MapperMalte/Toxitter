package examples.chat;

import java.util.TreeMap;

public class Reservoir<K extends Comparable<K>, V>
{
    private TreeMap<K,V> data = new TreeMap<>();

    public void put(K key, V value)
    {
        data.put(key,value);
    }

    public void delete(K key)
    {
        data.remove(key);
    }

    public V get(K key)
    {
        return data.get(key);
    }

    public boolean containsKey(K key)
    {
        return data.containsKey(key);
    }
}
