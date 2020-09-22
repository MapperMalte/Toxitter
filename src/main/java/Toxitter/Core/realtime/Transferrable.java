package Toxitter.Core.realtime;

public interface Transferrable
{
    public abstract String asJSON();

    public String asJavaScript();

    public String asJavaCode();
}
