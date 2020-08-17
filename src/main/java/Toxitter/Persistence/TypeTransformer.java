package Toxitter.Persistence;

public abstract class TypeTransformer
{
    public abstract String transform(Class type);
    public abstract String escape(String data, Class type);
}
