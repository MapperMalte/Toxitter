package Toxitter.Persistence.permanent.mysql;

import Toxitter.Persistence.annotations.Table;

import java.lang.reflect.Field;

public class MySqlStatementOctopus
{
    public MySqlStatement getCreateTableStatement()
    {
        return null;
    }

    public MySqlStatement getInsertValueStatement()
    {
        return null;
    }

    public MySqlStatement getDeleteValueStatement()
    {
        return null;
    }

    public MySqlStatement getUpdateValueStatement()
    {
        return null;
    }

    public static void serve(Class c)
    {

        if ( c.isAnnotationPresent(Table.class) )
        {
            Table t = (Table) c.getAnnotation(Table.class);
            String STATEMENT_PREFIX = "CREATE TABLE `toxitter`.`"+t.tableName()+"` (";
            String STATEMENT_POSTFIX = "PRIMARY KEY (`"+t.primaryKey()+"`));";
            StringBuilder STATEMENT_MIDDLEFIX = new StringBuilder();

            Field[] fields = c.getDeclaredFields();
            for( Field f: fields)
            {
                String type = f.getType().getName();
                System.out.println("Name: "+f.getName());
                System.out.println("Type: "+f.getType().getName());

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
