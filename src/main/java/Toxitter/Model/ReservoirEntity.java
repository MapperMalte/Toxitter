package Toxitter.Model;

import Toxitter.Persistence.PersistingOctopus;
import Toxitter.Persistence.annotations.Table;

import java.lang.reflect.Field;

public class ReservoirEntity implements Comparable
{
    public Table getTable()
    {
        return null;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}