package Toxitter.Elemental.water;

public abstract class TypeTransformer
{
    public abstract String transform(Class type);
    public abstract String escape(String data, Class type);
}
