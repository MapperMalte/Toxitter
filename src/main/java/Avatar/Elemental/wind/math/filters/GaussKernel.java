package Avatar.Elemental.wind.math.filters;

import Avatar.Elemental.wind.BookOfRass;
import Avatar.Elemental.wind.math.Filter;

public class GaussKernel implements Filter
{
    private double ThreeSigma = 0;

    public GaussKernel(double ThreeSigmaNeuronDistance)
    {
        this.ThreeSigma = ThreeSigmaNeuronDistance;
    }

    @Override
    public double filter(double value, double neuronDistance)
    {
        return BookOfRass.FastGauß.getValue(neuronDistance)*value;
    }
}