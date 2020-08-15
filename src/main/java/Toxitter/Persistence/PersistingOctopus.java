package Toxitter.Persistence;

import Toxitter.Persistence.annotations.Table;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.TreeMap;

public class PersistingOctopus
{
    public void getPersistor(Object o)
    {

    }

    public Method getGetter(String fieldname)
    {
        return null;
    }

    public Method getSetter(String fieldname)
    {
        return null;
    }

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

    public void testReservoirEntityFitsConventions(Class c)
    {
        System.out.println("Serving");
        if ( c.isAnnotationPresent(Table.class) )
        {
            Table t = (Table) c.getAnnotation(Table.class);
            String STATEMENT_PREFIX = "CREATE TABLE `toxitter`.`"+t.tableName()+"` (";
            String STATEMENT_POSTFIX = "PRIMARY KEY (`"+t.primaryKey()+"`));";
            StringBuilder STATEMENT_MIDDLEFIX = new StringBuilder();

            Field[] fields = c.getDeclaredFields();
            Method[] methods = c.getMethods();
            TreeMap<String,Method> indexedMethods = new TreeMap<>();
            for(Method m: methods)
            {
                indexedMethods.put(m.getName(),m);
            }
            for( Field f: fields)
            {
                String type = f.getType().getName();
                System.out.println("Name: "+f.getName());
                System.out.println("Type: "+f.getType().getName());

                if ( !indexedMethods.containsKey(getGetterName(f)) )
                    throw new IllegalArgumentException("Class "+c.getName()+" should have Getters" +
                            "and Setters for all fields, but it does not seem to have a getter for field "
                            +f.getName()+". It should be named "+getGetterName(f) );
                if ( !indexedMethods.containsKey(getSetterName(f)))
                    throw new IllegalArgumentException("Class "+c.getName()+" should have Getters" +
                            "and Setters for all fields, but it does not seem to have a setter for field "
                            +f.getName()+". It should be named "+getSetterName(f) );

                STATEMENT_MIDDLEFIX.append("`"+f.getName()+"` ");

                switch (type)
                {
                    case "java.lang.String":
                        STATEMENT_MIDDLEFIX.append("VARCHAR(255) NOT NULL, ");
                        break;
                    case "java.lang.Integer":
                }
            }
            System.out.println("Statement: ");
            System.out.println(STATEMENT_PREFIX+STATEMENT_MIDDLEFIX+STATEMENT_POSTFIX);
        }
    }
}
