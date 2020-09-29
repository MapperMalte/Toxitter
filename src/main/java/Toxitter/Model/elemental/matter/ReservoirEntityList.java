package Toxitter.Model.elemental.matter;

import Toxitter.Model.elemental.sky.DiamondList;

import java.util.TreeMap;

public class ReservoirEntityList<V extends ReservoirEntity> extends DiamondList<V>
{
    private TreeMap<ID,ValueBag<V>> index = new TreeMap<>();

    @Override
    public void addOnTop(V value)
    {
        if (index.containsKey(value.getId()) )
        {
            removeByKey(value.getId());
        }
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

    public boolean movePointerToKey(ID key)
    {
        if ( !index.containsKey(key) ) return false;
        this.pointer = index.get(key);
        return true;
    }

    public void removeByKey(ID key)
    {
        movePointerToKey(key);
        removeCurrent();
    }

    @Override
    public boolean contains(V element)
    {
        return index.containsKey(element.getId());
    }

    public void replace(ID key, V value)
    {
        movePointerToKey(key);
        removeCurrent();
        addOnTop(value);
    }

    public V getByKey(ID key)
    {
        return index.get(key).content;
    }
}