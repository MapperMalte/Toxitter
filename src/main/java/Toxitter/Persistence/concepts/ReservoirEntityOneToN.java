package Toxitter.Persistence.concepts;

import theory.DiamondList;

import java.util.Date;
import java.util.TreeMap;

public class ReservoirEntityOneToN<K, V>
{
    private TreeMap<K, DiamondList<V>> data = new TreeMap<>();
    private TreeMap<Date,K> requests;
    public void linkTreeMap(TreeMap<K, DiamondList<V>> treeMap)
    {
        this.data = treeMap;
    }

    public void add(K key, V value)
    {
        if ( data.containsKey(key) )
        {
            data.get(key).addOnTop(value);
        } else {
            DiamondList<V> dl = new DiamondList<>();
            data.put(key,dl);
            dl.addOnTop(value);
        }
    }

    public DiamondList<V> get(K key)
    {
        return null;
    }

    public void flash()
    {

    }
}
