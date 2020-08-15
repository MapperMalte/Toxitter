package Toxitter.Persistence.permanent.mysql;

import Toxitter.Persistence.TypeTransformer;

public class MySqlTypeTransformer extends TypeTransformer
{
    @Override
    public String transform(Class type) {
        switch (type.getName()){
            case "java.lang.String":
                return "VARCHAR(255) NOT NULL";
            case "java.lang.Integer":
                return "INT NOT NULL";
            case "java.lang.Double":
                return "DOUBLE NOT NULL";
        }
        throw new IllegalArgumentException("");
    }
}
