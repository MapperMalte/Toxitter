package Toxitter.Core.remake.dto.output;

public interface Transferrable
{
    public abstract String asJSON();

    public String asJavaScript();

    public String asJavaCode();
}
