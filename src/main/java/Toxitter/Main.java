package Toxitter;

import Toxitter.Core.ToxitterServer;
import Toxitter.Core.factoryfresh.ToxitterStandardConfiguratedEnvironment;

import java.io.IOException;

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
