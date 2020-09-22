package Toxitter.Model;

import theory.DiamondList;
import theory.TemporalQueue;

public class Many<K extends Comparable<K>,V>
{
    private TemporalQueue<K, DiamondList<V>> data = new TemporalQueue<>(10000, null);

    public void put(K key, V value)
    {
        if ( data.exists(key) )
        {
            DiamondList<V> existing = data.get(key);
            existing.addOnTop(value);
        } else {
            DiamondList<V> newDiamondList = new DiamondList<>();
            newDiamondList.addOnTop(value);
            data.put(key,newDiamondList);
        }
    }

    public boolean contains(K key)
    {
        return data.exists(key);
    }

    public DiamondList<V> get(K key)
    {
        return data.get(key);
    }
}