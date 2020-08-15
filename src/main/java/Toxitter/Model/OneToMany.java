package Toxitter.Model;

import Toxitter.Model.ReservoirEntity;
import theory.DiamondList;
import theory.TemporalQueue;

import java.util.TreeMap;

public class OneToMany<K extends Comparable,V extends ReservoirEntity>
{
    private TemporalQueue<K, DiamondList<V>> data;

    public void put(K key, V value)
    {
        value.getTable().primaryKey();

        DiamondList<V> dl = data.get(key);
        if ( dl == null )
        {
            dl = new DiamondList<V>();
            data.put(key,dl);
        }
        dl.addOnTop(value);
    }

    public DiamondList<V> read(K key)
    {

        return data.get(key);
    }

    public void update(K key, DiamondList<V> newValue)
    {

    }

    public void delete(K key)
    {

    }
}
