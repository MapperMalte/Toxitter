package Toxitter.Infusion;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import theory.ReplenisherStack;

public class Umlauter
{
    private static boolean init = false;

    public static String umlaut(String in)
    {
        System.out.println("FROM: "+in);
        checkInit();
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < in.length(); i++)
        {
            out.append(replacements[in.charAt(i)]);
        }
        System.out.println("TO: "+out.toString());
        return out.toString();
    }

    private static void checkInit()
    {
        if ( !init )
        {
            init();
            init = true;
        }
    }

    private static String[] replacements = new String[Character.MAX_VALUE];

    private static void umlaut(String from, String to)
    {
        replacements[from.charAt(0)]=to;
    }

    private static void init()
    {
        for(int i = 0; i < Character.MAX_VALUE; i++)
        {
            replacements[i] = Character.toString((char)i);
        }
        umlaut("Ö","Oe");
        umlaut("Ä","Ae");
        umlaut("Ü","Ue");
        umlaut("ü","ue");
        umlaut("ö","oe");
        umlaut("ä","ae");
    }
}