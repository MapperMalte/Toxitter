package Toxitter.Persistence;

import Toxitter.Model.ReservoirEntity;
import Toxitter.Persistence.annotations.Table;
import theory.DiamondList;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.TreeMap;

public class ReservoirEntityAnalyzer
{
    private TreeMap<String, Method> getters = new TreeMap<>();
    private TreeMap<String, Method> setters = new TreeMap<>();
    private TreeMap<String, Field> fieldIndex = new TreeMap<>();
    private Table table;
    private Field[] fields;

    public String getGetterName(Field f)
    {
        char first = f.getName().charAt(0);
        return "get"+Character.toUpperCase(first)+f.getName().substring(1);
    }

    public String getSetterName(Field f)
    {
        char first = f.getName().charAt(0);
        return "set"+Character.toUpperCase(first)+f.getName().substring(1);
    }

    public Table getTable()
    {
        return table;
    }

    public ReservoirEntityAnalyzer(Class c)
    {
        if ( c.isAnnotationPresent(Table.class) )
        {
            Table t = (Table) c.getAnnotation(Table.class);
            this.table = t;
            fields = c.getDeclaredFields();
            Method[] methods = c.getMethods();
            TreeMap<String,Method> indexedMethods = new TreeMap<>();
            for(Method m: methods)
            {
                indexedMethods.put(m.getName(),m);
            }
            for( Field f: fields)
            {
                String type = f.getType().getName();
                if (!indexedMethods.containsKey(getGetterName(f)))
                    throw new IllegalArgumentException("Class " + c.getName() + " should have Getters" +
                            "and Setters for all fields, but it does not seem to have a getter for field "
                            + f.getName() + ". It should be named " + getGetterName(f));
                if (!indexedMethods.containsKey(getSetterName(f)))
                    throw new IllegalArgumentException("Class " + c.getName() + " should have Getters" +
                            "and Setters for all fields, but it does not seem to have a setter for field "
                            + f.getName() + ". It should be named " + getSetterName(f));

                getters.put(f.getName(),indexedMethods.get(getGetterName(f)));
                fieldIndex.put(f.getName(),f);
                setters.put(f.getName(),indexedMethods.get(getSetterName(f)));
            }
        }
    }

    public static class DataAccessField
    {
        String fieldName;
        Class type;
    }

    public DiamondList<DataAccessToReservoirEntity.DataAccessField> getAllFields()
    {
        DiamondList<DataAccessToReservoirEntity.DataAccessField> dl = new DiamondList<>();
        for(Field f: fields)
        {
            DataAccessToReservoirEntity.DataAccessField daf = new DataAccessToReservoirEntity.DataAccessField();
            daf.fieldName = f.getName();
            daf.type = getType(daf.fieldName);
            dl.addOnTop(daf);
        }
        return dl;
    }

    public Class getType(String field)
    {
        return fieldIndex.get(field).getType();
    }
}
