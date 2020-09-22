package Toxitter.Core.realtime;

public interface TransferrableDataAtom
{
    public abstract String asJSON();

    public String asJavaScript();

    public String asJavaCode();
}
