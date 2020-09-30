package Toxitter.Boxfresh;

import Toxitter.Boxfresh.ToxitterStandardConfiguratedEnvironment;

public class Main
{
    public static void main(String[] args)
    {
        try {
            ToxitterStandardConfiguratedEnvironment.up();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
