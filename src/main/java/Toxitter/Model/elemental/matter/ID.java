package Toxitter.Model.elemental.matter;

import sun.util.PreHashedMap;

import java.util.TreeMap;

public class ID extends ReservoirEntity
{
    private String id = "";
    private static TreeMap<String, Boolean> IDs = new TreeMap<>();

    public ID()
    {
        id = makeId();
    }

    public ID(String string)
    {
        id = string;
        IDs.put(id,true);
    }

    @Override
    public String toString()
    {
        return id;
    }

    public ID merge(ID with)
    {
        return new ID(this.id+with.id);
    }

    public static ID fromString(String string)
    {
        return new ID(string);
    }

    public static boolean exists(ID id)
    {
        return IDs.containsKey(id.id);
    }

    public static String makeId()
    {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < 32; i++)
        {
            sb.append((char)(97+(Math.random()* (122-97))));
        }
        String result = sb.toString();
        if ( IDs.containsKey(result) )
        {
            return makeId();
        }
        IDs.put(result,true);
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
}
