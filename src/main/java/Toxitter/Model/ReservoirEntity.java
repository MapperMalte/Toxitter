package Toxitter.Model;

import Toxitter.Persistence.DataAccessToReservoirEntityBeingPersisted;
import Toxitter.Persistence.PersistingOctopus;
import Toxitter.Persistence.annotations.Table;

import java.lang.reflect.Field;

public abstract class ReservoirEntity<K extends Comparable>
{
    public Table getTable()
    {
        return null;
    }

    public DataAccessToReservoirEntityBeingPersisted getDataAccess()
    {
        return new DataAccessToReservoirEntityBeingPersisted(this);
    }

    public abstract K getId();
}