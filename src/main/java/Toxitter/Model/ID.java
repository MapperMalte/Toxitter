package Toxitter.Model;

public class ID implements Comparable {

    public static String makeId()
    {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < 32; i++)
        {
            sb.append((char)(97+(Math.random()* (122-97))));
        }
        return sb.toString();
    }

    public int makeIntId()
    {
        return (int)(Math.random()*((double)Integer.MAX_VALUE));
    }

    public long makeLongId()
    {
        return (long)(Math.random()*((double)Long.MAX_VALUE));
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
