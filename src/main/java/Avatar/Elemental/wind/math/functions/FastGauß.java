package Avatar.Elemental.wind.math.functions;

import Avatar.Elemental.wind.math.Function;

public class FastGauß implements Function
{
    private static final double arcaneConstant = (1+Math.sqrt(1-2*Math.PI*Math.PI+6*Math.PI))/(2*Math.PI);
    private static final double p0 = Math.sqrt(Math.PI/2);
    private static final double p1 = 1/Math.sqrt(2*Math.PI);
    private static final double p3 = Math.sqrt(2/Math.PI);
    private static final double p4 = p0*p0;
    private static final double p5 = Math.sqrt(2);
    private static final double p7 = 2*Math.PI*arcaneConstant*arcaneConstant;

    /**
     *
     * @param x
     * @return
     */
    public static double getErf(double x)
    {
        if ( x == 0 ) return 0;
        x = p5*x;
        double xSquared = x*x;
        double sdf = Math.exp(-xSquared/2);
        return 1-p3*(p0+(1/x)*(sdf-Math.sqrt(p4*xSquared+(sdf*Math.sqrt(1+p7*xSquared)/(1+arcaneConstant*xSquared)))));
    }

    public static double get(double x) {
        return Math.exp(-(x*x)/2)*p1;
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

    @Override
    public double getValue(double x)
    {
        return get(x);
    }

    public double getBigPhi(double x)
    {
        return 0.5*(1+getErf(x/p5));
    }

    @Override
    public double getDerivative(double x) {
        return get(x);
    }
}
