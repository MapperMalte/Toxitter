package Toxitter.Persistence.persistence.mysql;

import Toxitter.Persistence.TypeTransformer;

public class MySqlTypeTransformer extends TypeTransformer
{
    @Override
    public String transform(Class type) {
        switch (type.getName()){
            case "java.lang.String":
                return "VARCHAR(255) NOT NULL";
            case "java.lang.Integer":
            case "int":
                return "INT NOT NULL";
            case "java.lang.Double":
                return "DOUBLE NOT NULL";
        }
        System.out.println("Type: "+type.getName());
        throw new IllegalArgumentException("");
    }

    public String escape(String string, Class type)
    {
        switch (type.getName()){
            case "java.lang.String":
                return "\""+string+"\"";
        }
        return string;
    }
}
