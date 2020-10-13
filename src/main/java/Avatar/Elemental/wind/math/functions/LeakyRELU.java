package Avatar.Elemental.wind.math.functions;

import Avatar.Elemental.wind.math.Function;

public class LeakyRELU implements Function
{
    @Override
    public double getValue(double x ) {
        if ( x < 0 ) return x*0.1;
        return Math.max(0,x);
    }

    @Override
    public double getDerivative(double x) {
        if ( x < 0 ) return 0.1*x;
        return 1;
    }
}
