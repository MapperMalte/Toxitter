package Avatar.Elemental.wind.math.functions;

import Avatar.Elemental.wind.math.Function;

public class Sigmoid implements Function
{
    @Override
    public double getValue(double x) {
        return 1/(1+Math.exp(-x));
    }

    @Override
    public double getDerivative(double x) {
        return  getValue(x)*(1-getValue(x));
    }
}
