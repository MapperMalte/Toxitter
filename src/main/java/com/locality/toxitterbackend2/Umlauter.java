package com.locality.toxitterbackend2;

public class Umlauter
{
    public static String umlaut(String in)
    {
        return in.replaceAll("ö","\\u00F6");
    }
}