package Avatar.Elemental.wind.artifacts.gems;

import Avatar.Elemental.water.VectorSignal;

public class Sum extends ManyToOne
{
    @Override
    public double compute(VectorSignal signal)
    {
        double sum = 0;
        for(int i = 0; i < signal.data.length; i++)
        {
            sum += signal.data[i];
        }
        return sum;
    }
}
