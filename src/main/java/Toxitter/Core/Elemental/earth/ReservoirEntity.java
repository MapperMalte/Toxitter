package Toxitter.Core.Elemental.earth;

import Toxitter.Core.Elemental.water.DataAccessToReservoirEntity;

public abstract class ReservoirEntity implements Comparable<ReservoirEntity>
{
    protected ID id;

    public Persist getTable()
    {
        return (Persist) this.getClass().getAnnotation(Persist.class);
    }

    public DataAccessToReservoirEntity getDataAccess()
    {
        return new DataAccessToReservoirEntity(this);
    }

    public ID getId()
    {
        return id;
    }

    public void setId(ID key)
    {
        this.id = key;
    }

    @Override
    public int compareTo(ReservoirEntity o) {
        return o.id.compareTo(id);
    }
}