package Avatar.Elemental.wind.arcane;

import Avatar.Elemental.wind.BookOfRass;

public class FastGauß
{
    /**
     *
     * @param x
     * @return
     */
    public double getErf(double x)
    {
        return BookOfRass.getErf(x);
    }

    /**
     * Returns integral from minus infinity to x
     * @param x
     * @return
     */
    public double getCumulative(double x)
    {
        return 1/Math.exp(-358*x/23+111*Math.atan(37*x/294)+1);
    }
}
