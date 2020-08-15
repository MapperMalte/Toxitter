package Toxitter.Model;

import theory.TemporalQueue;

import java.util.TreeMap;

public class OneToOne<K extends Comparable,V extends ReservoirEntity>
{
    private TemporalQueue<K,V> data;
    private int maxNumberOfCachedElements;

    public OneToOne(int maxNumberOfCachedElements)
    {
        this.maxNumberOfCachedElements = maxNumberOfCachedElements;
        data = new TemporalQueue<>(this.maxNumberOfCachedElements,new NirvanaQueueSleeper<K,V>());
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
        data.delete(key);
    }
}
