package Toxitter.Model.elemental.matter.persistence.mysql;

import Toxitter.Model.elemental.matter.ID;
import Toxitter.Model.elemental.wind.TypeTransformer;

public class MySqlTypeTransformer extends TypeTransformer
{
    public static final String IDNAME = ID.class.getName();

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
        if ( type.equals(ID.class) )
        {
            return "VARCHAR(255) NOT NULL";
        }
        System.out.println("Type: "+type.getName());
        throw new IllegalArgumentException("");
    }

    @Override
    public String escape(String string, Class type)
    {
        switch (type.getName()){
            case "java.lang.String":
                return "\""+string+"\"";
        }
        if( type.equals(ID.class) )
        {
            return "\""+string+"\"";
        }
        return string;
    }
}
