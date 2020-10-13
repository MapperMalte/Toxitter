package Avatar.Elemental.wind.math.functions;

import Avatar.Elemental.wind.math.Function;

public class GELU implements Function {

    @Override
    public double getValue(double x) {
        return x*0.5*(1+FastGauß.getErf(x/Math.sqrt(2)));
    }

    @Override
    public double getDerivative(double x)
    {
        return 0.5*FastGauß.getErf(x/Math.sqrt(2))+0.398942*Math.exp(-(x*x)/2)*x+0.5;
    }
}
