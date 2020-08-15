package Toxitter.Model;

import theory.DiamondList;

import java.util.TreeMap;

public class ReservoirEntityList<K extends Comparable, V extends ReservoirEntity<K>> extends DiamondList<V>
{
    private TreeMap<K,ValueBag<V>> index = new TreeMap<>();

    @Override
    public void addOnTop(V value)
    {
        super.addOnTop(value);
        ValueBag<V> lastCreated = super.getLastCreatedValueBag();
        index.put(value.getId(),lastCreated);
    }

    @Override
    public void insertBeforePointer(V value)
    {
        super.insertBeforePointer(value);
        ValueBag<V> lastCreated = super.getLastCreatedValueBag();
        index.put(value.getId(),lastCreated);
    }

    @Override
    public void removeCurrent()
    {
        index.remove(this.getCurrent().getId());
        super.removeCurrent();
    }

    public void movePointerToKey(K key)
    {
        this.pointer = index.get(key);
    }

    public void replace(K key, V value)
    {
        movePointerToKey(key);
        removeCurrent();
        addOnTop(value);
    }

    public V getByKey(K key)
    {
        return index.get(key).content;
    }
}