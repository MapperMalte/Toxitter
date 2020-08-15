package Toxitter.Model;

import Toxitter.Model.ReservoirEntity;
import theory.DiamondList;

import java.util.TreeMap;

public class OneToMany<K extends Comparable,V extends ReservoirEntity> extends Relation<K, V>
{
    private TreeMap<K, DiamondList<V>> data = new TreeMap<>();

    public void putIfNotExists(K key, V value)
    {

    }

    public void put(K key, V value)
    {
        DiamondList dl = data.get(key);
        if ( dl == null )
        {
            dl = new DiamondList<V>();
            data.put(key,dl);
        }
        dl.addOnTop(value);
    }

    @Override
    public DiamondList<V> read(K key)
    {
        return null;
    }

    @Override
    public void update(K key, V newValue) {

    }

    public void update(K key, DiamondList<V> newValue)
    {

    }

    public void delete(K key)
    {

    }
}
