package Toxitter.Persistence;

import Toxitter.Model.Reservoir;
import Toxitter.Model.ReservoirEntity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.TreeMap;

public class Persistor<V>
{
    private TreeMap<String, Method> getters = new TreeMap<>();
    private TreeMap<String, Method> setters = new TreeMap<>();
    private TreeMap<String, Field> fields= new TreeMap<>();

    public void set(ReservoirEntity et, String field, V value)
    {
    }

    public Object get(ReservoirEntity et, String field)
    {
        try {
            return getters.get(field).invoke(et);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }

    public String getValueStringSeparatedBy(String separatedByWhat, TypeTransformer t)
    {
        return null;
    }

    public Class getType(ReservoirEntity et, String field)
    {
        return fields.get(field).getType();
    }
}