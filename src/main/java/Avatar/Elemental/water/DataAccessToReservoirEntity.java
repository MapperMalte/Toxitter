package Avatar.Elemental.water;

import Avatar.Elemental.earth.ID;
import Avatar.Elemental.earth.ReservoirEntity;
import Avatar.Elemental.earth.Persist;
import Avatar.Elemental.wind.artifacts.DiamondList;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.TreeMap;

public class DataAccessToReservoirEntity
{
    private ReservoirEntity object;
    private TreeMap<String, Method> getters = new TreeMap<>();
    private TreeMap<String, Method> setters = new TreeMap<>();
    private TreeMap<String, Field> fieldIndex = new TreeMap<>();
    private Persist table;
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

    public Persist getTable()
    {
        return table;
    }

    public DataAccessToReservoirEntity(ReservoirEntity o)
    {
        Class c = o.getClass();
        if ( c.isAnnotationPresent(Persist.class) )
        {
            Persist t = (Persist) c.getAnnotation(Persist.class);
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
    }

    public static class DataAccessField
    {
        public String fieldName;
        public Class type;
    }

    public DiamondList<DataAccessField> getAllFields()
    {
        DiamondList<DataAccessField> dl = new DiamondList<>();
        for(Field f: fields)
        {
            DataAccessField daf = new DataAccessField();
            daf.fieldName = f.getName();
            daf.type = getType(daf.fieldName);
            dl.addOnTop(daf);
        }
        return dl;
    }

    public Object get(String field)
    {
        try {
            System.out.println("Field: "+field);
            Object o = getters.get(field).invoke(object);
            if ( o.getClass().equals(ID.class) )
            {
                return o.toString();
            }
            return o;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }

    public Class getType(String field)
    {
        return fieldIndex.get(field).getType();
    }
}