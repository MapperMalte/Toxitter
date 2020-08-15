package Toxitter.Persistence;

import Toxitter.Model.ReservoirEntity;
import theory.DiamondList;

import javax.xml.crypto.Data;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.TreeMap;

public class DataAccessToReservoirEntityBeingPersisted
{
    private ReservoirEntity object;
    private TreeMap<String, Method> getters = new TreeMap<>();
    private TreeMap<String, Method> setters = new TreeMap<>();
    private TreeMap<String, Field> fields= new TreeMap<>();

    public DataAccessToReservoirEntityBeingPersisted(ReservoirEntity o)
    {
        this.object = o;
    }

    public void set(String field, Object value)
    {
        try {
           setters.get(field).invoke(object,value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }

    public static class DataAccessField
    {
        String fieldName;
        Class type;
    }

    public DiamondList<DataAccessField> getAllFields()
    {
        DiamondList<DataAccessField> dl = new DiamondList<>();
        return dl;
    }

    public Object get(String field)
    {
        try {
            return getters.get(field).invoke(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }

    public Class getType(ReservoirEntity et, String field)
    {
        return fields.get(field).getType();
    }
}