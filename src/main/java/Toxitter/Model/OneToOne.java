package Toxitter.Model;

import java.util.TreeMap;

public class OneToOne<K extends Comparable,V> extends Relation<K, V>
{
    private TreeMap<K,V> data = new TreeMap<>();

    public void putIfNotExists(K key, V value)
    {
        if ( !(data.get(key) == null) )
        {
            data.put(key, value);
        }
    }

    public void put(K key, V value)
    {
        data.put(key, value);
    }

    public V read(K key)
    {
        return data.get(key);
    }

    public void update(K key, V newValue)
    {
        data.put(key, newValue);
    }

    public void delete(K key)
    {
        data.remove(key);
    }
}
