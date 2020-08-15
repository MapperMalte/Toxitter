package Toxitter.Model;

import Toxitter.Persistence.DataAccessToReservoirEntity;

import Toxitter.Persistence.annotations.Table;

public abstract class ReservoirEntity<K extends Comparable>
{
    public Table getTable()
    {
        return null;
    }

    public DataAccessToReservoirEntity getDataAccess()
    {
        return new DataAccessToReservoirEntity(this);
    }

    public abstract K getId();
    public abstract void setId(K key);
}