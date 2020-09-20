package Toxitter.Core.remake;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ToxitterCLIReader
{
    public static void main(String[] args)
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String newLine = "";
        try {
            while ((newLine = br.readLine()) != null) {
                if ( newLine.equals("ToxitterCLI print routes") )
                {

                }
            }
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
