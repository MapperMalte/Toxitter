package Toxitter.Model;

import Toxitter.Model.ReservoirEntity;
import theory.DiamondList;
import theory.QueueSleeper;
import theory.TemporalQueue;

import java.util.TreeMap;

public class OneToMany<K extends Comparable,V extends ReservoirEntity<K>>
{
    private TemporalQueue<K, ReservoirEntityList<K,V>> data;

    public OneToMany(int maxCachedElementCount)
    {
        data = new TemporalQueue<>(maxCachedElementCount, new NirvanaQueueSleeper());
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

    public ReservoirEntityList<K,V> read(K key)
    {
        return data.get(key);
    }

    public void update(K key, ReservoirEntityList newValue)
    {
        data.put(key,newValue);
    }

    public void delete(K key)
    {
        data.delete(key);
    }
}
