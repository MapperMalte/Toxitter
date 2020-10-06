package Avatar.Elemental.wind.arcane.test;

import Avatar.Elemental.wind.arcane.FastGauß;
import org.junit.Test;

public class TestFastGauß
{
    @Test
    public void testSpeedErf()
    {
        FastGauß fastGauß = new FastGauß();
        long time1 = System.currentTimeMillis();
        for (long i = 0; i < 4000; i++ )
        {
            for(int x = -250; x <= 250; x++)
            {
                //BookOfRass.sigmoid(x*0.015);
                fastGauß.getErf((x*0.015));
            }
        }
        long time2 = System.currentTimeMillis();
        System.out.println("Time for drawing "+(4000*501)+" samples of the normal distribution using erf: "+(time2-time1)+" ms");
    }

    @Test
    public void testAccuracy()
    {
        FastGauß fastGauß = new FastGauß();
        for(int i = -250; i <= 250; i++)
        {
            System.out.println("Erf("+(i*0.015)+"): "+(FastGauß.getErf(i*0.015)));

            //System.out.println("G("+(i*0.015)+"): "+fastGauß.getErf(i*0.015));
        }
        System.out.println("Compare values with wolframalpha! ");
        System.out.println("https://www.wolframalpha.com");

    }
}
