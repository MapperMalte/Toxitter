package Avatar.Elemental.water;

import java.lang.reflect.Method;

public class BookOfIlaan
{
    public static final boolean active = true;

    public static void write(String s)
    {
        System.out.println(s);
    }

    public static void write(Class c, String s)
    {
        System.out.println(c.getName()+": "+s);
    }

    public static void write(Class c, Object o)
    {
        if ( o.getClass().equals(double[].class) )
        {
            write(c,printDouble((double[]) o));
        } else if ( o.getClass().equals(double[][].class) )
        {
            write(c,printDouble((double[][]) o));
        } else
        {
            write(o.toString());
        }
    }

    public static String printDouble(double[] data)
    {
        StringBuilder row = new StringBuilder();
        row.append("(");
        for(int x = 0 ; x < data.length; x++)
        {
            row.append(x).append(": ").append(data[x]);
            if ( !(x==data.length-1) )
            {
                row.append(", ");
            }
        }
        row.append(")");
        return row.toString();
    }

    public static String printDouble(double[][] data)
    {
        StringBuilder out = new StringBuilder();
        for(int i = 0; i < data.length; i++)
        {
            StringBuilder row = new StringBuilder();
            for(int x = 0 ; x < data[i].length; x++)
            {
                row.append("data[").append(i).append("][").append(x).append("]: ").append(data[i][x]);
                if ( !(x==data[i].length-1) )
                {
                    row.append(", ");
                }
            }
            out.append(row).append(System.getProperty("line.separator"));
        }
        return out.toString();
    }

    public static String printDouble(double[][][] data)
    {
        StringBuilder out = new StringBuilder();
        for(int i = 0; i < data.length; i++)
        {
            StringBuilder row = new StringBuilder();
            for(int x = 0 ; x < data[i].length; x++)
            {
                for(int y = 0; y < data[i][x].length; y++ )
                {
                    row.append("data[").append(i).append("][").append(x).append("][").append(y).append("]: ").append(data[i][x][y]);
                    if ( !(x==data[i].length-1) )
                    {
                        row.append(", ");
                    }
                }
                row.append(System.getProperty("line.separator"));
            }
            out.append(row).append(System.getProperty("line.separator"));
        }
        return out.toString();
    }


    public static void write(Class c, Method m, String s)
    {
        System.out.println(c.getName()+": "+s);
    }
}
