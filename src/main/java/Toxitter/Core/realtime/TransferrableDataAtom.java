package Toxitter.Core.realtime;

public abstract class TransferrableDataAtom
{
    public abstract String asJSON();

    public String asJavaScript()
    {
        return "";
    }

    public String asJavaCode()
    {
        return "";
    }

    @Override
    public String toString()
    {
        return this.asJSON();
    }
}
