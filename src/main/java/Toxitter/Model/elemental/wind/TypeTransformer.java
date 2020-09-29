package Toxitter.Model.elemental.wind;

public abstract class TypeTransformer
{
    public abstract String transform(Class type);
    public abstract String escape(String data, Class type);
}
