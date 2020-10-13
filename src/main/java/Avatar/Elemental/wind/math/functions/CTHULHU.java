package Avatar.Elemental.wind.math.functions;

import Avatar.Elemental.wind.math.Function;

public class CTHULHU implements Function
{
    @Override
    public double getValue(double x)
    {
        return 4*Math.exp(-x*x)*(x*x-1)/Math.sqrt(Math.PI);
    }

    @Override
    public double getDerivative(double x)
    {
        return 0;
    }
}