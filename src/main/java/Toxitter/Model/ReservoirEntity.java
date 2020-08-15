package Toxitter.Model;

import Toxitter.Persistence.PersistenceExecuter;
import Toxitter.Persistence.PersistingOctopus;
import Toxitter.Persistence.annotations.Table;

import java.lang.reflect.Field;

public class ReservoirEntity implements Comparable
{
    public Table getTable()
    {
        return null;
    }

    public PersistenceExecuter getPersistenceExecuter()
    {
        return null;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}