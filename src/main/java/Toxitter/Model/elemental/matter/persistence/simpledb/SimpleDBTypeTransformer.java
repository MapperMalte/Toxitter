package Toxitter.Model.elemental.matter.persistence.simpledb;

import Toxitter.Model.elemental.wind.TypeTransformer;

public class SimpleDBTypeTransformer extends TypeTransformer
{
    @Override
    public String transform(Class type) {
        switch (type.getName()){
            case "java.lang.String":
                return "str";
            case "java.lang.Integer":
            case "int":
                return "int";
            case "java.lang.Double":
                return "dbl";
        }
        System.out.println("Type: "+type.getName());
        throw new IllegalArgumentException("");
    }

    @Override
    public String escape(String data, Class type) {
        return data;
    }
}
