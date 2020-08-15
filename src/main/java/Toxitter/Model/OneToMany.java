package Toxitter.Model;

import theory.QueueSleeper;
import theory.TemporalQueue;

import java.util.TreeMap;

public class OneToMany<
            K extends Comparable,
            K2 extends Comparable,
            V extends ReservoirEntity<K2>>
{
    private TemporalQueue<K, ReservoirEntityList<K2,V>> data;

    public OneToMany(int maxCachedElementCount, QueueSleeper<K,ReservoirEntityList<K2, V>> queueSleeper)
    {
        data = new TemporalQueue<K, ReservoirEntityList<K2, V>>(maxCachedElementCount, queueSleeper);
    }

    public void put(K key, V value)
    {
        ReservoirEntityList dl = data.get(key);
        if ( dl == null )
        {
            dl = new ReservoirEntityList();
            data.put(key,dl);
        }
        dl.addOnTop(value);
    }

    public ReservoirEntityList<K2,V> get(K key)
    {
        return data.get(key);
    }

    public void update(K key, ReservoirEntityList<K2,V> newValue)
    {
        data.put(key,newValue);
    }

    public void putput(K firstkey, K2 secondKey, V newValue)
    {
        newValue.setId(secondKey);
        ReservoirEntityList<K2,V> dl = data.get(firstkey);
        if ( dl != null )
        {
            dl.replace(secondKey, newValue);
        }
    }

    public void delete(K key)
    {
        data.delete(key);
    }

    public V getget(K firstKey, K2 secondkey)
    {
        ReservoirEntityList<K2,V> dl = data.get(firstKey);
        if ( dl != null )
        {
            return dl.getByKey(secondkey);
        } else {
            return null;
        }
    }

    public void deletedelete(K firstKey, K2 secondKey)
    {
        ReservoirEntityList<K2,V> dl = data.get(firstKey);
        if ( dl != null )
        {
            dl.movePointerToKey(secondKey);
            dl.removeCurrent();
        }
    }
}
