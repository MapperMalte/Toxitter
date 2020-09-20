package Toxitter.Model;

import Toxitter.Persistence.DataAccessToReservoirEntity;

import Toxitter.Persistence.annotations.Table;

public abstract class ReservoirEntity<K extends Comparable> implements Comparable<K>
{
    private K id;

    public Table getTable()
    {
        return (Table) this.getClass().getAnnotation(Table.class);
    }

    public DataAccessToReservoirEntity getDataAccess()
    {
        return new DataAccessToReservoirEntity(this);
    }

    public K getId()
    {
        return id;
    }

    public  void setId(K key)
    {
        this.id = key;
    }

    @Override
    public int compareTo(K o) {
        return o.compareTo(id);
    }
}