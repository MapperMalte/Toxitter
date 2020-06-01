package Toxitter.Logging;

public class Ullog
{
    public static final boolean active = true;

    public static final void put(String s)
    {
        System.out.println(s);
    }
    public static final void put(Class c, String s)
    {
        System.out.println(c.getName()+": "+s);
    }
}
