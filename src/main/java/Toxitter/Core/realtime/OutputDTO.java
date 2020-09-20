package Toxitter.Core.realtime;

public abstract class OutputDTO
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
}